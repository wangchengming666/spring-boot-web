package com.web.framework.common.utils;


import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

/**
 * hibernate-validator校验工具类
 *
 * 参考文档：http://docs.jboss.org/hibernate/validator/5.4/reference/en-US/html_single/
 *
 */
public final class ValidatorUtil {
  private static Validator validator;

  private static String DEFUALT_DELIMITER = ",";

  static {
    validator = Validation.buildDefaultValidatorFactory().getValidator();
  }

  /**
   * 
   * @param object
   * @param groups
   */
  public static void validateMayThrows(Object object, Class<?>... groups) {
    Optional<String> errors = validate(object, DEFUALT_DELIMITER, groups);
    if (errors.isPresent()) {
      throw new IllegalArgumentException(errors.get());
    }
  }

  /**
   * 
   * @param object
   * @param groups
   */
  public static Optional<String> validate(Object object, Class<?>... groups) {
    return validate(object, DEFUALT_DELIMITER, groups);
  }

  /**
   * 
   * @param object
   * @param delimiter
   * @param groups
   */
  public static Optional<String> validate(Object object, String delimiter, Class<?>... groups) {
    Objects.requireNonNull(object);
    Set<ConstraintViolation<Object>> constraintViolations = validator.validate(object, groups);
    if (!constraintViolations.isEmpty()) {
      return Optional.of(constraintViolations.stream().map(t -> t.getMessage())
          .collect(Collectors.joining(delimiter)));
    } else {
      return Optional.ofNullable(null);
    }
  }


}
