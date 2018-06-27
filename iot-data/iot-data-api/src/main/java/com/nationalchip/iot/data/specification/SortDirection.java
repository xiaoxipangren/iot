package com.nationalchip.iot.data.specification;

import com.nationalchip.iot.data.exception.UnsupportedDirectionException;

/**
 * @Author: zhenghq
 * @Description:
 * @Date: 6/26/18 12:49 PM
 * @Modified:
 */
public enum SortDirection {
    /**
     * Ascending.
     */
    ASC,
    /**
     * Descending.
     */
    DESC;

    /**
     * Returns the sort direction for the given name.
     *
     * @param name
     *            the name of the enum
     * @return the corresponding enum
     * @throws UnsupportedDirectionException
     *             if there is no matching enum for the specified name
     */
    public static SortDirection from(final String name) {
        try {
            return valueOf(name.toUpperCase());
        } catch (final IllegalArgumentException ex) {// NOSONAR
            throw new UnsupportedDirectionException();
        }
    }
}
