package jp.co.rakuten.ecommerce.api.service;

import jp.co.rakuten.ecommerce.api.entity.Review;
import jp.co.rakuten.ecommerce.api.exception.NotFoundException;
import jp.co.rakuten.ecommerce.api.repository.ReviewRepository;
import jp.co.rakuten.ecommerce.common.dto.IdDto;
import jp.co.rakuten.ecommerce.common.dto.MessageDto;
import jp.co.rakuten.ecommerce.common.dto.ReviewDto;
import jp.co.rakuten.ecommerce.common.dto.ReviewListDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class ReviewService {

    private ReviewRepository reviewRepository;

    public ReviewListDto getReviews() {
        Iterable<Review> reviews = reviewRepository.findAll();

        ReviewListDto reviewListDto = ReviewListDto.builder().build();

        reviews.forEach(item -> reviewListDto.addReview(getReviewDto(item)));

        return reviewListDto;
    }

    public ReviewListDto getReviewsByCusItem(Integer cus, Integer item) {
        Iterable<Review> reviews = reviewRepository.findItemByCustomerIdAndItemId(cus, item);

        ReviewListDto reviewListDto = ReviewListDto.builder().build();

        reviews.forEach(i -> reviewListDto.addReview(getReviewDto(i)));

        return reviewListDto;
    }

    public ReviewDto getReviewById(Integer id) {
        Optional<Review> review = reviewRepository.findById(id);

        if (review.isEmpty()) {
            throw new NotFoundException("Review not found for id=" + id);
        }

        return getReviewDto(review.get());
    }

    @Transactional
    public IdDto createReview(ReviewDto reviewDto) {
        Review review = getReview(reviewDto);
        review = reviewRepository.save(review);
        return IdDto.builder().id(review.getId()).build();
    }

    @Transactional
    public MessageDto updateReview(ReviewDto reviewDto) {
        Review review = getReview(reviewDto);
        review = reviewRepository.save(review);
        return MessageDto.builder().message("Success").build();
    }

    private ReviewDto getReviewDto(Review review) {
        return ReviewDto.builder().id(review.getId())
                .customerId(review.getCustomerId())
                .itemId(review.getItemId())
                .orderId(review.getOrderId())
                .rating(review.getRating())
                .review(review.getReview())
                .cusName(review.getLastName() + " " + review.getFirstName())
                .build();
    }

    private Review getReview(ReviewDto reviewDto) {
        Review review = new Review();
        review.setId(reviewDto.getId());
        review.setCustomerId(reviewDto.getCustomerId());
        review.setItemId(reviewDto.getItemId());
        review.setOrderId(reviewDto.getOrderId());
        review.setRating(reviewDto.getRating());
        review.setReview(reviewDto.getReview());
        review.setFirstName(reviewDto.getCusName().split(" ")[1]);
        review.setLastName(reviewDto.getCusName().split(" ")[0]);
        return review;
    }
}
