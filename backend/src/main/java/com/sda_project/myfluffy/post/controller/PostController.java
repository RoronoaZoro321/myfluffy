package com.sda_project.myfluffy.post.controller;

import com.sda_project.myfluffy.post.dto.PostCreationDto;
import com.sda_project.myfluffy.post.dto.PostUpdateDto;
import com.sda_project.myfluffy.common.dto.response.ResponseDto;
import com.sda_project.myfluffy.common.utils.constants.AppConstants;
import com.sda_project.myfluffy.post.dto.PostDto;
import com.sda_project.myfluffy.post.service.IPostService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/posts", produces = { MediaType.APPLICATION_JSON_VALUE })
@AllArgsConstructor
public class PostController {

    private IPostService iPostService;

    @PostMapping
    public ResponseEntity<ResponseDto> createPost(@RequestBody PostCreationDto postCreationDto,
            @AuthenticationPrincipal OAuth2User principal) {
        iPostService.createPost(postCreationDto, principal);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseDto.builder()
                        .statusCode(AppConstants.STATUS_201)
                        .statusMsg(AppConstants.MESSAGE_201)
                        .build());
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> fetchAllPosts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        List<PostDto> postDtos = iPostService.fetchAllPosts(page, size, sortBy, sortDir);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> fetchPostById(@PathVariable int id) {
        PostDto postDto = iPostService.fetchPostById(id);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postDto);
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<PostDto>> fetchPostsByOwnerId(
            @PathVariable int ownerId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        List<PostDto> postDtos = iPostService.fetchPostsByOwnerId(ownerId, page, size, sortBy, sortDir);
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(postDtos);
    }

    @Operation(summary = "Get My Post: OAuth2User")
    @GetMapping("/me")
    public ResponseEntity<List<PostDto>> fetchMyPostDetails(@AuthenticationPrincipal OAuth2User principal) {
        List<PostDto> postDtos = iPostService.fetchMyPost(principal);
        return ResponseEntity.status(HttpStatus.OK)
                .body(postDtos);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDto> deletePostById(@PathVariable int id) {
        boolean isDeleted = iPostService.deletePostById(id);
        if (isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ResponseDto.builder()
                            .statusCode(AppConstants.STATUS_200)
                            .statusMsg(AppConstants.MESSAGE_200)
                            .build());
        } else {
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(ResponseDto.builder()
                            .statusCode(AppConstants.STATUS_417)
                            .statusMsg(AppConstants.MESSAGE_417_DELETE)
                            .build());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updatePost(@PathVariable int id, @RequestBody PostUpdateDto postUpdateDto) {
        // postUpdateDto.setId(id); // Ensure the ID is set from the path
        boolean isUpdated = iPostService.updatePost(id, postUpdateDto);
        if (isUpdated) {
            return new ResponseEntity<>("Post updated successfully!", HttpStatus.OK);
        }
        return new ResponseEntity<>("Failed to update post.", HttpStatus.BAD_REQUEST);
    }

}
