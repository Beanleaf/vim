package be.beanleaf.vim.app.utils;

import static org.assertj.core.api.Assertions.assertThat;

import be.beanleaf.vim.domain.UserRole;
import org.junit.jupiter.api.Test;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;

class WebUtilsTest {

  @Test
  void getGravatarImgSrc() {
    String withDefault = WebUtils.getGravatarImgSrc("mail@mail.com", 50, "defaultString");
    assertThat(withDefault).startsWith("https://").contains("gravatar").contains("avatar")
        .contains("50").contains("defaultString");
    String withoutDefault = WebUtils.getGravatarImgSrc("mail@mail.com", 50);
    assertThat(withoutDefault).startsWith("https://").contains("gravatar").contains("avatar")
        .contains("50").contains("blank");
  }


  @Test
  void getExactPatternMatch() {
    assertThat(WebUtils.getExactPatternMatch(null)).isEqualTo("");
    assertThat(WebUtils.getExactPatternMatch("test")).isEqualTo("[t][e][s][t]");
  }

  @Test
  void getDownloadHeaders() {
    HttpHeaders headers = WebUtils.getDownloadHeaders("filename.txt");
    assertThat(headers.getCacheControl()).isNotNull();
    assertThat(headers.getContentDisposition()).isEqualTo(
        ContentDisposition.builder("attachment").filename("filename.txt").build()
    );
  }

  @Test
  void getAllInheritedRoles() {
    assertThat(WebUtils.getAllInheritedRoles(UserRole.STANDARD)).containsExactly(UserRole.STANDARD);
    assertThat(WebUtils.getAllInheritedRoles(UserRole.ADMIN)).hasSizeGreaterThan(1);
  }
}