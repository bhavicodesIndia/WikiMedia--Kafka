package com.bhaviCodes.wikimediaConsumer.Entity;
import jakarta.persistence.*;  // ✅ correct for Boot 3+
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
@Table(schema = "wikiMedia")
public class wikiMediaData {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Lob
    private String wikiMediaData;
}
