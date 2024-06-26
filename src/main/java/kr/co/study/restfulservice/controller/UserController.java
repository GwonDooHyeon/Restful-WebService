package kr.co.study.restfulservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import kr.co.study.restfulservice.bean.User;
import kr.co.study.restfulservice.dao.UserDaoService;
import kr.co.study.restfulservice.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequiredArgsConstructor
@Tag(name = "user-controller", description = "일반 사용자 서비스를 위한 컨트롤러")
public class UserController {

    private final UserDaoService service;


    @GetMapping("/users")
    public List<User> findAllUsers() {
        return service.findAll();
    }

    @Operation(summary = "사용자 정보 조회 API", description = "사용자 ID를 이용해서 사용자 상세 정보를 조회 합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "OK !!"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SEVER ERROR")
    }
    )
    @GetMapping("/users/{id}")
    public EntityModel<User> findUser(@Parameter(description = "사용자 아이디", required = true, example = "1") @PathVariable int id) {
        User user = service.findOne(id);
        if (user == null) {
            throw new UserNotFoundException("ID[%s] not found".formatted(id));
        }

        EntityModel<User> entityModel = EntityModel.of(user);

        WebMvcLinkBuilder linkTo = linkTo(methodOn(this.getClass()).findAllUsers());
        entityModel.add(linkTo.withRel("all-users"));

        return entityModel;
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User savedUser = service.save(user);

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedUser.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Object> createUser(@PathVariable int id) {
        User deletedUser = service.deleteById(id);

        if (deletedUser == null) {
            throw new UserNotFoundException("ID[%s] not found".formatted(id));
        }

        return ResponseEntity.noContent().build();
    }

}
