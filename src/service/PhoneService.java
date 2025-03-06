package service;

import dto.PhoneCreateDto;
import dto.PhoneDto;
import dto.PhoneListDTO;
import entity.Phone;
import repository.PhoneRepository;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

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

    public PhoneDto getPhoneByName(Connection conn, String name) {
        return Optional.ofNullable(phoneRepository.findByName(conn, name))
                .map(phone -> new PhoneDto(
                        phone.getPhoneId(),
                        phone.getName(),
                        phone.getBrand(),
                        phone.getRegularPrice(),
                        phone.getDiscountAmount(),
                        phone.getStock()))
                .orElse(null);
    }

    public void editStock(Connection conn, String name, int newStock) {
        Phone phone = phoneRepository.findByName(conn, name);

        if (phone == null) {
            System.out.println("해당 이름의 기종이 존재하지 않습니다.");
            return;
        }

        phoneRepository.updateStock(conn, phone.getPhoneId(), phone.getStock() + newStock);
    }
}
