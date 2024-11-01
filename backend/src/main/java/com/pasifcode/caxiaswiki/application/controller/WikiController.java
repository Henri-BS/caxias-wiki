package com.pasifcode.caxiaswiki.application.controller;

import com.pasifcode.caxiaswiki.application.mapper.WikiMapper;
import com.pasifcode.caxiaswiki.domain.dto.WikiDto;
import com.pasifcode.caxiaswiki.domain.entity.Wiki;
import com.pasifcode.caxiaswiki.service.interf.WikiService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/v1/wikis")
@RequiredArgsConstructor
public class WikiController {

    private final WikiService wikiService;
    private final WikiMapper wikiMapper;

    @PostMapping
    public ResponseEntity saveWiki(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("imageUrl") String imageUrl,
            @RequestParam("tags") List<String> tags
    ) throws IOException {
        Wiki wiki = wikiMapper.mapToWiki(name, description, imageUrl, tags);
        wikiService.saveWiki(wiki);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<WikiDto> findWikiById(@PathVariable String id) {
        var wiki = wikiService.findById(id);
        var dto = wikiMapper.wikiToDto(wiki);
        return ResponseEntity.ok(dto);
    }

    @GetMapping
    public ResponseEntity<Page<WikiDto>> searchWikis(
            @RequestParam(required = false, defaultValue = "") String query,
            Pageable pageable) {
        var result = wikiService.searchWikis( pageable);
        var wikis = result.map(wikiMapper::wikiToDto);
        return ResponseEntity.ok(wikis);
    }
}