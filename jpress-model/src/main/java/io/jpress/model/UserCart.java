package io.jpress.model;

import io.jboot.db.annotation.Table;
import io.jpress.model.base.BaseUserCart;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Generated by JPress.
 */
@Table(tableName = "user_cart", primaryKey = "id")
public class UserCart extends BaseUserCart<UserCart> {

    private static final long serialVersionUID = 1L;


    public BigDecimal getShouldPayPrice() {

        BigDecimal newestSalePrice = getBigDecimal("newestSalePrice");
        if (newestSalePrice == null) {
            newestSalePrice = getProductPrice();
        }

        if (newestSalePrice == null || newestSalePrice.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ZERO;
        }

        BigDecimal memberPrice = getBigDecimal("memberPrice");

        //只有会员价更加便宜的时候，走会员价
        if (memberPrice != null
                && memberPrice.compareTo(BigDecimal.ZERO) >= 0
                && memberPrice.compareTo(newestSalePrice) < 0) {
            return memberPrice.multiply(BigDecimal.valueOf(getProductCount()));
        }


        return newestSalePrice.multiply(BigDecimal.valueOf(getProductCount()));
    }


    public BigDecimal getNewProductPrice(){
        BigDecimal newestSalePrice = getBigDecimal("newestSalePrice");
        return newestSalePrice == null ? getProductPrice() : newestSalePrice;
    }


    public UserFavorite toFavorite() {
        UserFavorite userFavorite = new UserFavorite();
        userFavorite.setCreated(new Date());
        userFavorite.setUserId(getUserId());
        userFavorite.setThumbnail(getProductThumbnail());
        userFavorite.setTitle(getProductTitle());
        userFavorite.setSummary(getProductSummary());
        userFavorite.setLink(getProductLink());

        userFavorite.setType(getProductType());
        userFavorite.setTypeText(getProductTypeText());
        userFavorite.setTypeId(getProductId() == null ? null : String.valueOf(getProductId()));

        return userFavorite;
    }


}
