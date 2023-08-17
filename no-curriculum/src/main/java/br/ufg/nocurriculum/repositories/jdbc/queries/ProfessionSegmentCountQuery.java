package br.ufg.nocurriculum.repositories.jdbc.queries;

public interface ProfessionSegmentCountQuery {

    String COUNT_TALENTS_BY_SEGMENT = """
            SELECT name, COUNT(*) AS talents_count
            FROM
            	(SELECT DISTINCT ps.name,
            			email
            		FROM profession_segment ps
            		LEFT JOIN (segment_default_profession sdp
            		        JOIN default_profession sp ON sp.id = sdp.profession_segment_id
            		    ) ON ps.id = sdp.default_profession_id
            		LEFT JOIN profession p ON (p.name = sp.name)
            		LEFT JOIN user_profile up ON (up.id = p.profile_id)
            	) AS temp
            GROUP BY (name);
        """.trim();
}
