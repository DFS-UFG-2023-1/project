package br.ufg.nocurriculum.entities;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ProfessionSegmentCount {

    private String name;
    private Long count;

    public ProfessionSegmentCount(String name, Long count) {
        this.name = name;
        this.count = count;
    }
}
