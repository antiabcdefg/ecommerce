package jp.co.rakuten.ecommerce.api.controller;

import io.swagger.annotations.Api;
import jp.co.rakuten.ecommerce.api.service.ReviewService;
import jp.co.rakuten.ecommerce.common.dto.IdDto;
import jp.co.rakuten.ecommerce.common.dto.MessageDto;
import jp.co.rakuten.ecommerce.common.dto.ReviewDto;
import jp.co.rakuten.ecommerce.common.dto.ReviewListDto;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags="ReviewController")
@RestController
@AllArgsConstructor
public class ReviewController {

    ReviewService reviewService;

    @GetMapping("/reviews")
    public ReviewListDto getReviews() {
        return reviewService.getReviews();
    }

    @GetMapping("/review/{id}")
    public ReviewDto getReviews(@PathVariable Integer id) {
        return reviewService.getReviewById(id);
    }

    @GetMapping("/review/{cus}/{item}")
    public ReviewListDto getReviewsByCusItem(@PathVariable Integer cus, @PathVariable Integer item) {
        return reviewService.getReviewsByCusItem(cus, item);
    }

    @PostMapping("/reviews")
    public IdDto createReview(@Validated @RequestBody ReviewDto reviewDto) {
        return reviewService.createReview(reviewDto);
    }

    @PutMapping("/reviews")
    public MessageDto updateReview(@Validated @RequestBody ReviewDto reviewDto) {
        return reviewService.updateReview(reviewDto);
    }
}
