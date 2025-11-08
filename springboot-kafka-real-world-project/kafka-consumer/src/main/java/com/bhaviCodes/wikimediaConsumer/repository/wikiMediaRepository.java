package com.bhaviCodes.wikimediaConsumer.repository;

import com.bhaviCodes.wikimediaConsumer.Entity.wikiMediaData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface wikiMediaRepository  extends JpaRepository<wikiMediaData, Long> {

}