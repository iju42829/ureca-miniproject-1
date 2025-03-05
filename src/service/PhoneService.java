package service;

import dto.PhoneCreateDto;
import entity.Phone;
import repository.PhoneRepository;

import java.sql.Connection;

public class PhoneService {

    private final PhoneRepository phoneRepository;

    public PhoneService(PhoneRepository phoneRepository) {
        this.phoneRepository = phoneRepository;
    }

    public void createPhone(Connection conn, PhoneCreateDto phoneCreateDto) {
        Phone phone = new Phone(phoneCreateDto.getName(),
                phoneCreateDto.getBrand(),
                phoneCreateDto.getRegularPrice(),
                phoneCreateDto.getDiscountAmount(),
                phoneCreateDto.getStock());

        phoneRepository.save(conn, phone);
    }
}
