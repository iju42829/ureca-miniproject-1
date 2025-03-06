package service;

import dto.PhoneCreateDto;
import dto.PhoneListDTO;
import entity.Phone;
import repository.PhoneRepository;

import java.sql.Connection;
import java.util.List;

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

    public List<PhoneListDTO> getAllPhones(Connection conn) {
        return phoneRepository.findAll(conn)
                .stream()
                .map(phone -> new PhoneListDTO(
                        phone.getName(),
                        phone.getBrand(),
                        phone.getRegularPrice(),
                        phone.getDiscountAmount(),
                        phone.getStock()))
                .toList();
    }
}
