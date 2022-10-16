package fan.company.serverforotm.service;

import fan.company.serverforotm.entity.BaseUpdated;
import fan.company.serverforotm.entity.Role;
import fan.company.serverforotm.entity.Users;
import fan.company.serverforotm.exceptions.ResourceNotFoundException;
import fan.company.serverforotm.payload.ApiResult;
import fan.company.serverforotm.payload.RegisterDto;
import fan.company.serverforotm.repository.BaseUpdatedRepository;
import fan.company.serverforotm.repository.DivisionRepository;
import fan.company.serverforotm.repository.RoleRepository;
import fan.company.serverforotm.repository.UserRepository;
import fan.company.serverforotm.security.PasswordValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository repository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    PasswordValidator passwordValidator;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    DivisionRepository divisionRepository;


    public ApiResult register(RegisterDto dto) {

        if (!dto.getPassword().equals(dto.getPrePassword()))
            return new ApiResult("Passwordlar mos emas!", false);

        if (repository.findByUsername(dto.getUsername()).isPresent())
            return new ApiResult("Bunday username mavjud!", false);

        if (!passwordValidator.isValid(dto.getPassword())) return new ApiResult("Password mustahkam emas!", false);

        Users user = new Users(
                dto.getFullName(),
                dto.getUsername(),
                passwordEncoder.encode(dto.getPassword()),
                roleRepository.findById(dto.getRoleId()).orElseThrow(() -> new ResourceNotFoundException(
                        "role", "id", dto.getRoleId()
                )),
                divisionRepository.findById(dto.getDivisionId()).orElseThrow(() -> new ResourceNotFoundException(
                        "division", "id", dto.getDivisionId()
                ))
        );

        repository.save(user);

        return new ApiResult("Muvoffaqiyatli ro'yxatdan o'tkazildi!", true);

    }

    public ApiResult edit(Long id, RegisterDto dto) {

        Optional<Users> optionalUser = repository.findById(id);

        if (optionalUser.isPresent())
            return new ApiResult("Bunday username mavjud!", false);

        Optional<Role> optionalRole = roleRepository.findById(dto.getRoleId());
        if (optionalRole.isEmpty())
            return new ApiResult("Bunday Role mavjud emas!", false);

        if (!passwordValidator.isValid(dto.getPassword())) return new ApiResult("Password mustahkam emas!", false);

        Users user = new Users(
                dto.getFullName(),
                dto.getUsername(),
                passwordEncoder.encode(dto.getPassword()),
                roleRepository.findById(dto.getRoleId()).orElseThrow(() -> new ResourceNotFoundException(
                        "role", "id", dto.getRoleId()
                )),

                divisionRepository.findById(dto.getDivisionId()).orElseThrow(() -> new ResourceNotFoundException(
                        "division", "id", dto.getDivisionId()
                ))
        );

        user.setId(optionalUser.get().getId());

        repository.save(user);
        return new ApiResult("Muvoffaqiyatli tahrirlandi!", true);
    }

    public Page<Users> getAll(Integer page) {
        Pageable pageable = PageRequest.of(page, 10);
        return repository.findAll(pageable);
    }

    public Users getOne(Long id) {
        return repository.findById(id).orElse(null);
    }

    public ApiResult delete(Long id) {

        try {
            boolean existsById = repository.existsById(id);
            if (!existsById)
                return new ApiResult("Bunday User mavjud emas", false);

                repository.deleteById(id);

                return new ApiResult("O'chirildi", true);

        } catch (Exception e) {
            return new ApiResult("Xatolik", false);
        }
    }

}
