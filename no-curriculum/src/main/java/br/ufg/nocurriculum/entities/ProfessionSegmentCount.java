package br.ufg.nocurriculum.entities;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Builder
@Data
public class ProfessionSegmentCount {

    private String name;
    private Long talentsCount;

    public ProfessionSegmentCount(String name, Long talentsCount) {
        this.name = name;
        this.talentsCount = talentsCount;
    }
}
