package jp.co.rakuten.ecommerce.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewListDto {

    private List<ReviewDto> reviewDtos;

    public void addReview(ReviewDto reviewDto) {
        if (reviewDtos == null) {
            reviewDtos = new ArrayList<>();
        }
        reviewDtos.add(reviewDto);
    }
}
