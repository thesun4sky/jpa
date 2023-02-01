package me.whitebear.jpa.common;

import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@RequiredArgsConstructor
@AllArgsConstructor
@Builder
public class PageDTO {

  private final Integer currentPage;
  private final Integer size;
  private String sortBy;

  public Pageable toPageable() {
    if (Objects.isNull(sortBy)) {
      return PageRequest.of(currentPage - 1, size);
    } else {
      return PageRequest.of(currentPage - 1, size, Sort.by(sortBy).descending());
    }
  }

  public Pageable toPageable(String sortBy) {
    return PageRequest.of(currentPage - 1, size, Sort.by(sortBy).descending());
  }
}