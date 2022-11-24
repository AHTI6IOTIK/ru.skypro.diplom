package ru.skypro.diplom.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.skypro.diplom.dto.ads.AdsDto;
import ru.skypro.diplom.dto.ads.AdsCommentDto;
import ru.skypro.diplom.dto.ads.ResponseWrapperAdsCommentDto;
import ru.skypro.diplom.service.AdsCommentService;
import ru.skypro.diplom.service.AdsService;

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
public class AdsCommentController {
    private final AdsService adsService;
    private final AdsCommentService adsCommentService;

    public AdsCommentController(
        AdsService service,
        AdsCommentService adsCommentService
    ) {
        this.adsService = service;
        this.adsCommentService = adsCommentService;
    }

    @GetMapping(value = "/{ad_pk}/comment")
    @Operation(
        summary = "getAdsComments",
        responses = {
            @ApiResponse(responseCode = "200", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "404", content = @Content())
        }
    )
    public ResponseWrapperAdsCommentDto getAdsComments(
        @PathVariable("ad_pk") long adPk
    ) {
        ResponseWrapperAdsCommentDto ads = adsCommentService.getAdsComments(adPk);
        if (null == ads) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return ads;
    }

    @PostMapping(value = "/{ad_pk}/comment")
    @Operation(
        summary = "addAdsComments",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "comment"),
        responses = {
            @ApiResponse(responseCode = "200", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "201", content = @Content()),
            @ApiResponse(responseCode = "404", content = @Content())
        }
    )
    public AdsCommentDto createAdsComments(
        @PathVariable("ad_pk") long adPk,
        @RequestBody AdsCommentDto comment
    ) {
        AdsDto adsDto = adsService.findById(adPk);
        if (null == adsDto) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return adsCommentService.addAdsComment(adPk, comment);
    }

    @DeleteMapping(value = "/{ad_pk}/comment/{id}")
    @Operation(
        summary = "deleteAdsComment",
        responses = @ApiResponse(responseCode = "204", content = @Content())
    )
    public HttpStatus deleteAdsComments(
        @PathVariable("ad_pk") long adPk,
        @PathVariable("id") long commentId
    ) {
        AdsDto adsDto = adsService.findById(adPk);
        if (null == adsDto) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        if (adsCommentService.deleteAdsComment(adPk, commentId)) {
            return HttpStatus.NO_CONTENT;
        }

        return HttpStatus.FORBIDDEN;
    }

    @GetMapping(value = "/{ad_pk}/comment/{id}")
    @Operation(
        summary = "getAdsComment",
        responses = {
            @ApiResponse(responseCode = "200", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "404", content = @Content())
        }
    )
    public AdsCommentDto getAdsComment(
        @PathVariable("ad_pk") long adPk,
        @PathVariable("id") long commentId
    ) {
        AdsCommentDto adsCommentDto = adsCommentService.getAdsComment(adPk, commentId);

        if (null == adsCommentDto) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return adsCommentDto;
    }

    @PatchMapping(value = "/{ad_pk}/comment/{id}")
    @Operation(
        summary = "updateAdsComment",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "comment"),
        responses = {
            @ApiResponse(responseCode = "200", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "204", content = @Content()),
            @ApiResponse(responseCode = "404", content = @Content())
        }
    )
    public AdsCommentDto updateAdsComment(
        @PathVariable("ad_pk") long adPk,
        @PathVariable("id") long commentId,
        @RequestBody AdsCommentDto updatedAdsCommentDto
    ) {
        AdsDto adsDto = adsService.findById(adPk);
        AdsCommentDto adsCommentDto = adsCommentService.findById(commentId);
        if (null == adsDto || null == adsCommentDto) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        boolean updateStatus = adsCommentService.updateAdsComment(adsDto, adsCommentDto, updatedAdsCommentDto);
        if (!updateStatus) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT);
        }

        return updatedAdsCommentDto;
    }
}
