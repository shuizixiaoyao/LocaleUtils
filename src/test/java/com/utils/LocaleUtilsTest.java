package com.utils;

import org.junit.Before;
import org.junit.Test;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


public class LocaleUtilsTest {
    private LocaleUtils localeUtils;

    @Before
    public void setUp() {
        localeUtils = new LocaleUtils();
    }

    @Test
    public void should_return_null_when_str_is_null() {
        //given
        //when
        Locale result = localeUtils.toLocale(null);
        //then
        assertThat(result).isNull();
    }

    @Test
    public void should_return_empty_local_when_str_is_empty() {
        //given
        //when
        Locale result = localeUtils.toLocale("");
        //then
        assertThat(result).isEqualToComparingFieldByField(new Locale("", ""));
    }

    @Test
    public void should_throw_exception_when_str_contain_octothorpe() {
        //given
        String testString = "ab#";
        //when
        //then
        assertThatThrownBy(() -> localeUtils.toLocale(testString)).hasMessage("Invalid locale format: " +
                testString);
    }

    @Test
    public void should_throw_exception_when_str_length_less_2() {
        //given
        String testString = "a";
        //when
        //then
        assertThatThrownBy(() -> localeUtils.toLocale(testString)).hasMessage("Invalid locale format: " +
                testString);
    }

    @Test
    public void should_throw_exception_when_str_begin_with_underline_and_length_less_3() {
        //given
        String testString = "_a";
        //when
        //then
        assertThatThrownBy(() -> localeUtils.toLocale(testString)).hasMessage("Invalid locale format: " +
                testString);
    }

    @Test
    public void should_throw_exception_when_str_begin_with_underline_and_char_number_1_is_lower_case() {
        //given
        String testString = "_aB";
        //when
        //then
        assertThatThrownBy(() -> localeUtils.toLocale(testString)).hasMessage("Invalid locale format: " +
                testString);
    }

    @Test
    public void should_throw_exception_when_str_begin_with_underline_and_char_number_2_is_lower_case() {
        //given
        String testString = "_Ab";
        //when
        //then
        assertThatThrownBy(() -> localeUtils.toLocale(testString)).hasMessage("Invalid locale format: " +
                testString);
    }

    @Test
    public void should_return_right_local_when_str_begin_with_underline_and_length_is_3() {
        //given
        String testString = "_AB";
        //when
        Locale result = localeUtils.toLocale(testString);
        //then
        assertThat(result).isEqualToComparingFieldByField(new Locale("",
                testString.substring(1, 3)));
    }

    @Test
    public void should_throw_exception_when_str_begin_with_underline_and_length_is_4() {
        //given
        String testString = "_ABC";
        //when
        //then
        assertThatThrownBy(() -> localeUtils.toLocale(testString)).hasMessage("Invalid locale format: " +
                testString);
    }

    @Test
    public void should_throw_exception_when_str_begin_with_underline_and_char_number_3_is_not_underline() {
        //given
        String testString = "_ABCD";
        //when
        //then
        assertThatThrownBy(() -> localeUtils.toLocale(testString)).hasMessage("Invalid locale format: " +
                testString);
    }

    @Test
    public void should_return_right_local_when_str_begin_with_underline_and_all_validations_passed() {
        //given
        String testString = "_AB_C";
        //when
        Locale result = localeUtils.toLocale(testString);
        //then
        assertThat(result).isEqualToComparingFieldByField(new Locale("",
                testString.substring(1, 3), testString.substring(4)));
    }

    @Test
    public void should_return_right_local_when_str_not_begin_with_underline_and_is_all_lower_case_and_length_is_2() {
        //given
        String testString = "ab";
        //when
        Locale result = localeUtils.toLocale(testString);
        //then
        assertThat(result).isEqualToComparingFieldByField(new Locale(testString));
    }

    @Test
    public void should_return_right_local_when_str_not_begin_with_underline_and_is_all_lower_case_and_length_is_3() {
        //given
        String testString = "abc";
        //when
        Locale result = localeUtils.toLocale(testString);
        //then
        assertThat(result).isEqualToComparingFieldByField(new Locale(testString));
    }

    @Test
    public void should_throw_exception_when_segments_length_is_2_but_country_is_empty() {
        //given
        String testString = "ab_";
        //when
        //then
        assertThatThrownBy(() -> localeUtils.toLocale(testString)).hasMessage("Invalid locale format: " +
                testString);
    }

    @Test
    public void should_throw_exception_when_segments_length_is_2_but_country_is_not_ISO3166_and_not_numeric() {
        //given
        String testString = "ab_cd";
        //when
        //then
        assertThatThrownBy(() -> localeUtils.toLocale(testString)).hasMessage("Invalid locale format: " +
                testString);
    }

    @Test
    public void should_return_right_local_when_language_is_ISO639_and_country_is_ISO3166() {
        //given
        String testString = "ab_CD";
        //when
        Locale result = localeUtils.toLocale(testString);
        //then
        assertThat(result).isEqualToComparingFieldByField(new Locale("ab", "CD"));
    }

    @Test
    public void should_return_right_local_when_language_is_not_ISO639_and_country_is_numeric() {
        //given
        String testString = "aB_123";
        //when
        Locale result = localeUtils.toLocale(testString);
        //then
        assertThat(result).isEqualToComparingFieldByField(new Locale("aB", "123"));
    }

    @Test
    public void should_return_right_local_when_language_is_ISO639_and_country_is_empty_and_variant_is_not_empty() {
        //given
        String testString = "ab__E";
        //when
        Locale result = localeUtils.toLocale(testString);
        //then
        assertThat(result).isEqualToComparingFieldByField(new Locale("ab", "", "E"));
    }

    @Test
    public void should_return_right_local_when_language_is_ISO639_and_country_is_numeric_and_variant_is_not_empty() {
        //given
        String testString = "ab_123_E";
        //when
        Locale result = localeUtils.toLocale(testString);
        //then
        assertThat(result).isEqualToComparingFieldByField(new Locale("ab", "123", "E"));
    }

    @Test
    public void should_return_right_local_when_language_is_ISO639_and_country_is_ISO3166_and_variant_is_not_empty() {
        //given
        String testString = "ab_CD_E";
        //when
        Locale result = localeUtils.toLocale(testString);
        //then
        assertThat(result).isEqualToComparingFieldByField(new Locale("ab", "CD", "E"));
    }

    @Test
    public void should_throw_exception_when_language_is_ISO639_and_country_is_ISO3166_and_variant_is_empty() {
        //given
        String testString = "ab_CD_";
        //when
        //then
        assertThatThrownBy(() -> localeUtils.toLocale(testString)).hasMessage("Invalid locale format: " +
                testString);
    }

    @Test
    public void should_throw_exception_when_language_is_not_ISO639_and_country_is_ISO3166_and_variant_is_not_empty() {
        //given
        String testString = "aB_CD_E";
        //when
        //then
        assertThatThrownBy(() -> localeUtils.toLocale(testString)).hasMessage("Invalid locale format: " +
                testString);
    }

    @Test
    public void should_throw_exception_when_language_is_not_ISO639_as_length_and_country_is_ISO3166() {
        //given
        String testString = "abbbbb_CD_E";
        //when
        //then
        assertThatThrownBy(() -> localeUtils.toLocale(testString)).hasMessage("Invalid locale format: " +
                testString);
    }

    @Test
    public void should_throw_exception_when_language_is_ISO639_and_country_is_not_ISO3166_and_variant_is_not_empty() {
        //given
        String testString = "ab_cD_E";
        //when
        //then
        assertThatThrownBy(() -> localeUtils.toLocale(testString)).hasMessage("Invalid locale format: " +
                testString);
    }

    @Test
    public void should_throw_exception_when_language_is_ISO639_and_country_is_not_ISO3166_as_length() {
        //given
        String testString = "ab_CDDDDDD_E";
        //when
        //then
        assertThatThrownBy(() -> localeUtils.toLocale(testString)).hasMessage("Invalid locale format: " +
                testString);
    }

    @Test
    public void should_throw_exception_when_language_is_ISO639_and_country_is_numeric_but_length_is_not_3() {
        //given
        String testString = "ab_123456_E";
        //when
        //then
        assertThatThrownBy(() -> localeUtils.toLocale(testString)).hasMessage("Invalid locale format: " +
                testString);
    }

    @Test
    public void should_throw_exception_when_segments_length_is_greater_3() {
        //given
        String testString = "ab_CD_E_F";
        //when
        //then
        assertThatThrownBy(() -> localeUtils.toLocale(testString)).hasMessage("Invalid locale format: " +
                testString);
    }
}