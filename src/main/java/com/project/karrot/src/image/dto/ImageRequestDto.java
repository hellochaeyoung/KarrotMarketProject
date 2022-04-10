package com.project.karrot.src.image.dto;

import com.project.karrot.src.image.ImageFiles;
import com.project.karrot.src.image.MemberImageFile;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ImageRequestDto {

    private Long memberId;
    private Long productId;
    private String fileName;
    private String fileOriName;
    private String fileURL;

}
