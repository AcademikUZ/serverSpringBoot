package fan.company.serverforotm.controller;

import fan.company.serverforotm.annotation.RoleniTekshirish;
import fan.company.serverforotm.entity.Division;
import fan.company.serverforotm.entity.Users;
import fan.company.serverforotm.payload.ApiResult;
import fan.company.serverforotm.payload.DivisionDto;
import fan.company.serverforotm.payload.RegisterDto;
import fan.company.serverforotm.service.DivisionService;
import fan.company.serverforotm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api/division")
public class DivisionController {

    @Autowired
    DivisionService service;

    /**
     * @RoleniTekshirish foydalanuvchi roli tekshiriladi
     * Boshqarma qo'shish uchun kerak
     * DivisionDto da faqat name keladi
     * @return  ApiResult orqali natija qaytadi true or false
     */


    @RoleniTekshirish(role = "ADMIN")
    @PostMapping
    public HttpEntity<?> add(@Valid @RequestBody DivisionDto dto){
        ApiResult apiResult = service.add(dto);
        return ResponseEntity.status(apiResult.isSuccess()? HttpStatus.CREATED:HttpStatus.CONFLICT).body(apiResult);
    }

    /**
     * @RoleniTekshirish foydalanuvchi roli tekshiriladi
     * Boshqarma nomini o'zgartirish uchun kerak uchun kerak
     * @param id Boshqarma id si keladi
     * @param dto  da faqat name keladi
     * @return  ApiResult orqali natija qaytadi true or false
     */

    @RoleniTekshirish(role = "ADMIN")
    @PutMapping("/{id}")
    public HttpEntity<?> editRole(@PathVariable Long id, @Valid @RequestBody DivisionDto dto) {
        ApiResult apiResult = service.edit(id, dto);
        return ResponseEntity.status(apiResult.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResult);
    }

    /**
     * barcha boshqarmalar ro'yxatini olish uchun ishlatiladi
     * @param page sahifalab berish uchun ishlatiladi (page) faqat integer keladi //misol  GET http://localhost:8080/api/division?page=0
     * @return  ApiResult orqali natija qaytadi true or false
     */

    @RoleniTekshirish(role = "ADMIN, USER")
    @GetMapping
    public HttpEntity<?> getAll(@RequestParam Integer page) {
        Page<Division> all = service.getAll(page);
        return ResponseEntity.status(!all.isEmpty()? HttpStatus.OK:HttpStatus.CONFLICT).body(all);
    }

    /**
     * bitta boshqarma ro'yxatini olish uchun ishlatiladi
     * @param id Boshqarma id si keladi
     * @return Division qaytadi
     */

    @RoleniTekshirish(role = "ADMIN, USER")
    @GetMapping("/{id}")
    public HttpEntity<?> getOne(@PathVariable Long id) {
        Division one = service.getOne(id);
        return ResponseEntity.status(one != null? HttpStatus.OK:HttpStatus.CONFLICT).body(one);
    }

    /**
     * bitta boshqarmani o'chirish uchun ishlatiladi
     * delete qilish uchun unga bog'liq barcha narsani o'chirish kerak
     * @param id Boshqarma id si keladi
     * @return ApiResult orqali natija qaytadi true or false
     */


    @RoleniTekshirish(role = "ADMIN")
    @DeleteMapping("/{id}")
    public HttpEntity<?> delete(@PathVariable Long id) {
        ApiResult apiResult = service.delete(id);
        return ResponseEntity.status(apiResult.isSuccess()? HttpStatus.NO_CONTENT:HttpStatus.CONFLICT).body(apiResult);
    }



}
