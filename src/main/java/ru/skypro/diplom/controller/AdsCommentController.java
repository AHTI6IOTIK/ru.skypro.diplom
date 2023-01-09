package ru.skypro.diplom.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.skypro.diplom.dto.ads.AdsCommentDto;
import ru.skypro.diplom.dto.ads.ResponseWrapperAdsCommentDto;
import ru.skypro.diplom.exception.NotFoundCommentException;
import ru.skypro.diplom.exception.UpdateCommentForbidden;
import ru.skypro.diplom.service.AdsCommentService;

import javax.annotation.security.RolesAllowed;
import javax.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin(value = "http://localhost:3000")
@RequestMapping("/ads")
@Tag(name = "Объявления", description = "Ads controller")
@ApiResponses(
    value = {
        @ApiResponse(responseCode = "401", content = @Content()),
        @ApiResponse(responseCode = "403", content = @Content())
    }
)
@SecurityRequirement(name = "basicAuth")
public class AdsCommentController {
    private final AdsCommentService adsCommentService;

    public AdsCommentController(
        AdsCommentService adsCommentService
    ) {
        this.adsCommentService = adsCommentService;
    }

    @GetMapping(value = "/{ad_pk}/comments")
    @Operation(
        summary = "getAdsComments",
        responses = {
            @ApiResponse(responseCode = "200", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "404", content = @Content())
        }
    )
    @RolesAllowed({"USER"})
    public ResponseWrapperAdsCommentDto getAdsComments(
        @PathVariable("ad_pk") long adPk
    ) {
        ResponseWrapperAdsCommentDto ads = adsCommentService.getAdsComments(adPk);
        if (null == ads) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return ads;
    }

    @PostMapping(value = "/{ad_pk}/comments")
    @Operation(
        summary = "addAdsComments",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "comment"),
        responses = {
            @ApiResponse(responseCode = "200", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "201", content = @Content()),
            @ApiResponse(responseCode = "404", content = @Content())
        }
    )
    @RolesAllowed({"USER"})
    public AdsCommentDto createAdsComments(
        @PathVariable("ad_pk") long adPk,
        @RequestBody AdsCommentDto comment,
        Authentication authentication
    ) {
        AdsCommentDto adsComment = adsCommentService.addAdsComment(
            adPk,
            authentication.getName(),
            comment
        );

        if (null == adsComment) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return adsComment;
    }

    @DeleteMapping(value = "/{ad_pk}/comments/{id}")
    @Operation(
        summary = "deleteAdsComment",
        responses = @ApiResponse(responseCode = "204", content = @Content())
    )
    @RolesAllowed({"USER"})
    public void deleteAdsComments(
        @PathVariable("ad_pk") long adPk,
        @PathVariable("id") long commentId,
        Authentication authentication,
        HttpServletResponse response
    ) {
        try {
            adsCommentService.deleteAdsComment(
                authentication.getName(),
                adPk,
                commentId
            );

            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        } catch (NotFoundCommentException exception) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        response.setStatus(HttpServletResponse.SC_OK);
    }

    @PatchMapping(value = "/{ad_pk}/comments/{id}")
    @Operation(
        summary = "updateAdsComment",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "comment"),
        responses = {
            @ApiResponse(responseCode = "200", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "404", content = @Content())
        }
    )
    @RolesAllowed({"USER"})
    public AdsCommentDto updateAdsComment(
        @PathVariable("ad_pk") long adPk,
        @PathVariable("id") long commentId,
        @RequestBody AdsCommentDto updatedAdsCommentDto,
        Authentication authentication
    ) {
        try {
            AdsCommentDto updatedDto = adsCommentService.updateAdsComment(
                authentication.getName(),
                adPk,
                commentId,
                updatedAdsCommentDto
            );

            if (null == updatedDto) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            }

            return updatedDto;
        } catch (UpdateCommentForbidden e) {
            throw new ResponseStatusException(
                HttpStatus.FORBIDDEN,
                e.getMessage()
            );
        }
    }

    @GetMapping(value = "/{ad_pk}/comments/{id}")
    @Operation(
        summary = "getAdsComment",
        responses = {
            @ApiResponse(responseCode = "200", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "404", content = @Content())
        }
    )
    @RolesAllowed({"USER"})
    public AdsCommentDto getAdsComment(
        @PathVariable("ad_pk") long adPk,
        @PathVariable("id") long commentId
    ) {
        AdsCommentDto adsCommentDto = adsCommentService.getAdsComment(
            adPk,
            commentId
        );

        if (null == adsCommentDto) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return adsCommentDto;
    }
}
