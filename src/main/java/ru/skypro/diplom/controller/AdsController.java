package ru.skypro.diplom.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import ru.skypro.diplom.dto.ads.*;
import ru.skypro.diplom.service.AdsService;

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
public class AdsController {
    private final AdsService adsService;

    public AdsController(
        AdsService service
    ) {
        this.adsService = service;
    }

    @GetMapping
    @Operation(
        summary = "getALLAds",
        responses = @ApiResponse(responseCode = "200", useReturnTypeSchema = true)
    )
    public ResponseWrapperAdsDto getAllAds() {
        return adsService.getAllAds();
    }

    @PostMapping
    @Operation(
        summary = "addAds",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "createAds"),
        responses = {
            @ApiResponse(responseCode = "200", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "201", content = @Content())
        }
    )
    @RolesAllowed({"USER"})
    public AdsDto createAds(
        @RequestBody CreateAdsDto createAdsDto,
        Authentication authentication
    ) {
        AdsDto adsDto = adsService.createAds(authentication.getName(), createAdsDto);

        if (null == adsDto) {
            throw new ResponseStatusException(HttpStatus.CREATED);
        }

        return adsDto;
    }

    @GetMapping(value = "/me")
    @Operation(
        summary = "getAdsMe",
        responses = {
            @ApiResponse(responseCode = "200", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "404", content = @Content())
        }
    )
    @RolesAllowed({"USER"})
    public ResponseWrapperAdsDto getMyAds(
        @RequestParam(required = false) boolean authenticated,
        @RequestParam(required = false, name = "authorities[0].authority") String authority,
        @RequestParam(required = false) Object credentials,
        @RequestParam(required = false) Object details,
        @RequestParam(required = false) Object principal,
        Authentication authentication
    ) {
        ResponseWrapperAdsDto ads = adsService.getMyAds(
            authentication.getName(),
            authenticated,
            authority,
            credentials,
            details,
            principal
        );

        if (null == ads) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return ads;
    }

    @GetMapping(value = "/{id}")
    @Operation(
        summary = "getAds",
        responses = {
            @ApiResponse(responseCode = "200", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "404", content = @Content())
        }
    )
    @RolesAllowed({"USER"})
    public FullAdsDto getAds(
        @PathVariable("id") long adsId
    ) {
        FullAdsDto fullAdsDto = adsService.getAds(
            adsId
        );

        if (null == fullAdsDto) {
            throw new ResponseStatusException(
                HttpStatus.NOT_FOUND
            );
        }

        return fullAdsDto;
    }

    @DeleteMapping(value = "/{id}")
    @Operation(
        summary = "removeAds",
        responses = {
            @ApiResponse(responseCode = "204", content = @Content())
        }
    )
    @RolesAllowed({"USER"})
    public void removeAds(
        @PathVariable("id") long adsId,
        Authentication authentication,
        HttpServletResponse response
    ) {
        boolean isRemoveAds = adsService.removeAds(
            adsId,
            authentication.getName()
        );

        if (!isRemoveAds) {
            response.setStatus(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
    }

    @PatchMapping(value = "/{id}")
    @Operation(
        summary = "updateAds",
        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "ads"),
        responses = {
            @ApiResponse(responseCode = "200", useReturnTypeSchema = true),
            @ApiResponse(responseCode = "204", content = @Content()),
            @ApiResponse(responseCode = "404", content = @Content())
        }
    )
    @RolesAllowed({"USER"})
    public AdsDto updateAds(
        @PathVariable("id") long adsId,
        @RequestBody AdsDto updatedAdsDto,
        Authentication authentication
    ) {
        AdsDto updatedAds = adsService.updateAds(
            authentication.getName(),
            adsId,
            updatedAdsDto
        );

        if (null == updatedAds) {
            throw new ResponseStatusException(
                HttpStatus.NO_CONTENT
            );
        }

        return updatedAds;
    }
}
