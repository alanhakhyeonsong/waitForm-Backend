package me.ramos.WaitForm.domain.recommend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RecommendRequest {

    private List<ClusteredMember> members;
    private Long boardId;
}
