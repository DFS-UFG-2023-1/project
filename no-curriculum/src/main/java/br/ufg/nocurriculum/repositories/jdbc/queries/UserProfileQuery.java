package br.ufg.nocurriculum.repositories.jdbc.queries;

public interface UserProfileQuery {

    String SELECT_TALENTS_BY_TERM = """
             WITH users_id AS
                (WITH seg AS
                        (SELECT ps.name AS ps_name,
                                dp.name AS dp_name
                            FROM profession_segment ps
                            JOIN segment_default_profession sdp ON ps.id = sdp.default_profession_id
                            JOIN default_profession dp ON dp.id = sdp.profession_segment_id)
                SELECT DISTINCT up.id
                   FROM user_profile up
                   JOIN users u ON u.username = up.email
                   LEFT JOIN profession pn ON pn.profile_id = up.id
                   LEFT JOIN seg ON seg.dp_name = pn.name
                   WHERE up.email ILIKE :term
                       OR up.social_name ILIKE :term
                       OR dp_name ILIKE :term
                       OR ps_name ILIKE :term
                       OR pn.name ILIKE :term
                   GROUP BY up.id)
            SELECT up.social_name, up.email, string_agg(pn.name, ',') AS professions
            FROM user_profile up,
                profession pn,
                users_id
            WHERE pn.profile_id = up.id
                AND up.visible = TRUE
                AND up.id IN (users_id.id)
            GROUP BY up.id, up.social_name, up.email;
        """.trim();

    String SELECT_TALENTS_BY_SEGMENT = """
            WITH seg AS
                (SELECT ps.name AS ps_name,
                        dp.name AS dp_name
                    FROM profession_segment ps
                    JOIN segment_default_profession sdp ON ps.id = sdp.default_profession_id
                    JOIN default_profession dp ON dp.id = sdp.profession_segment_id)
            SELECT up.social_name, up.email, string_agg(pn.name, ',') AS professions
            FROM user_profile up
            JOIN users u ON u.username = up.email
            LEFT JOIN profession pn ON pn.profile_id = up.id
            LEFT JOIN seg ON seg.dp_name = pn.name
            WHERE ps_name = :name
            GROUP BY up.id, up.social_name, up.email;
        """.trim();
}
