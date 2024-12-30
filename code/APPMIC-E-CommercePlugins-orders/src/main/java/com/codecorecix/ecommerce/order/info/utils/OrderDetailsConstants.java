package com.codecorecix.ecommerce.order.info.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderDetailsConstants {

  public static final String NO_EXIST_ORDER_ID_IN_BD = "Ups! The OrderId does not exist or has no order details.";

  public static final String DETAILS_ORDER_FOUND = "Order details found in database";

}
