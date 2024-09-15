package com.github.supercoding.service;

import com.github.supercoding.respository.airlineTicket.AirlineTicket;
import com.github.supercoding.respository.airlineTicket.AirlineTicketAndFlightInfo;
import com.github.supercoding.respository.airlineTicket.AirlineTicktetRepository;
import com.github.supercoding.respository.passenger.Passenger;
import com.github.supercoding.respository.passenger.PassengerRepository;
import com.github.supercoding.respository.reservations.Reservation;
import com.github.supercoding.respository.reservations.ReservationRepository;
import com.github.supercoding.respository.users.UserEntity;
import com.github.supercoding.respository.users.UserRepository;
import com.github.supercoding.service.exceptions.InvalidValueException;
import com.github.supercoding.service.exceptions.NotAcceptException;
import com.github.supercoding.service.exceptions.NotFoundException;
import com.github.supercoding.service.mapper.TicketMapper;
import com.github.supercoding.web.dto.airline.ReservationRequest;
import com.github.supercoding.web.dto.airline.ReservationResult;
import com.github.supercoding.web.dto.airline.Ticket;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AirReservationService {

    private UserRepository userRepository;

    private AirlineTicktetRepository airlineTicktetRepository;

    private PassengerRepository passengerRepository;
    private ReservationRepository reservationRepository;

    public AirReservationService(UserRepository userRepository, AirlineTicktetRepository airlineTicktetRepository, PassengerRepository passengerRepository, ReservationRepository reservationRepository) {
        this.userRepository = userRepository;
        this.airlineTicktetRepository = airlineTicktetRepository;
        this.passengerRepository = passengerRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<Ticket> findUserFavoritePlaceTickets(Integer userId, String ticketType) {
        //필요한 Repository: UserRepository, airLineTicket Repository
        //1. 유저를  userId 로 가져와서, 선호하는 여행지 알아야한다 도출..
        //2. 선호하는 여행지와 ticketType으로 AirlineTicket table 질의 해서 필요한 AirlineTicket 들고오기/
        //3. 이 둘의 정보를 조합해서 Ticket DTO를 만든다.

        Set<String> ticketTypeSet = new HashSet<>(Arrays.asList("편도", "왕복"));

        if ( !ticketTypeSet.contains(ticketType) )
            throw new InvalidValueException("해당 TicketType " + ticketType + "은 지원하지 않습니다.");

        UserEntity userEntity = userRepository.findUserById(userId).orElseThrow(()-> new NotFoundException("해당ID : " + userId + "유저를 찾을수 없습니다."));
        String likePlace = userEntity.getLikeTravelPlace();

        //티케 테이블 여러개 가져오기
        List<AirlineTicket> airlineTickets  // AirlineTicket 엔티티 만들어줘야함 빨간불 없앨라면
                = airlineTicktetRepository.findAllAirlineTicketsWithPlaceAndTicketType(likePlace, ticketType);

        if(airlineTickets.isEmpty()) throw new NotFoundException("해당 likePlace: " + likePlace + " 와 TicketType: " + ticketType + "에 해당하는 항공권 찾을 수 없습니다.");

        //티켓 DTO 만들어야함
        List<Ticket> tickets = airlineTickets.stream().map(TicketMapper.INSTANCE::airlineTicketToTicket).collect(Collectors.toList());
        return tickets;                           //  .map(Ticket::new) 위와같이 맵퍼사용
    }

    @Transactional(transactionManager = "tm2")
    public ReservationResult makeReservation(ReservationRequest reservationRequest) {
        // 1. Reservation Repository,Passenger Repository, Join table ( flight/airline_ticket)

        // 0, userId, airline_ticket_id
        Integer userId = reservationRequest.getUserId();
        Integer airlineTicketId = reservationRequest.getAirlineTicketId();

        // 1. Passenger Id
        Passenger passenger = passengerRepository.findPassengerByUserId(userId).orElseThrow(() -> new NotFoundException("요청하신  userId " + userId + "에 해당하는 Passenger를 찾을 수 없습니다."));
        Integer passengerId = passenger.getPassengerId();

        // 2.  price 등의 정보 불러오기
        List<AirlineTicketAndFlightInfo> airlineTicketAndFlightInfos
                = airlineTicktetRepository.findAllAirLineTicketAndFlightInfo(airlineTicketId);

        if(airlineTicketAndFlightInfos.isEmpty())
            throw new NotFoundException("AirlineTicket Id " + airlineTicketId + " 에 해당하는 항공편과 항공권 찾을 수 없습니다.");

        // 3. reservation 생성
        Boolean isSuccess = false;
        Reservation reservation = new Reservation(passengerId, airlineTicketId);
        try {
            isSuccess = reservationRepository.saveReservation(reservation);
        }catch (RuntimeException e) {
            throw new NotAcceptException("Reservation이 등록되는 과정이 거부되었습니다.");
        }

        // ReservationResult DTO 만들기.
        List<Integer> prices = airlineTicketAndFlightInfos.stream().map(AirlineTicketAndFlightInfo::getPrice).collect(Collectors.toList());
        List<Integer> charges = airlineTicketAndFlightInfos.stream().map(AirlineTicketAndFlightInfo::getCharge).collect(Collectors.toList());
        Integer tax = airlineTicketAndFlightInfos.stream().map(AirlineTicketAndFlightInfo::getTax).findFirst().get();
        Integer totalPrice = airlineTicketAndFlightInfos.stream().map(AirlineTicketAndFlightInfo::getTotalPrice).findFirst().get();

        return new ReservationResult(prices, charges, tax, totalPrice, isSuccess);
    }

}
