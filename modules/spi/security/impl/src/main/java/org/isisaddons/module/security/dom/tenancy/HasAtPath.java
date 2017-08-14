package org.isisaddons.module.security.dom.tenancy;

/**
 * Role interface for domain objects to implement, indicating that these are characteristics of the entity that
 * can be used to determine its visibility/editability.
 *
 * <p>
 *     Previously the <code>atPath</code> would have corresponded to the unique path of some particular
 *     {@link org.isisaddons.module.security.dom.tenancy.ApplicationTenancy} instance.  However, this has now been
 *     generalized; the atPath is simply a string whose interpretation is application-specific (in particular by
 *     the {@link ApplicationTenancyEvaluator} SPI).
 * </p>
 *
 * <p>
 * For applications that still wish to follow the original more specific design (that the <code>atPath</code>
 * corresponds to a single {@link ApplicationTenancy}), then the path can be interpreted according to the following
 * table:
 * </p>
 * <table border="1">
 *     <tr>
 *         <th>object's tenancy</th><th>user's tenancy</th><th>access</th>
 *     </tr>
 *     <tr>
 *         <td>null</td><td>null</td><td>editable</td>
 *     </tr>
 *     <tr>
 *         <td>null</td><td>non-null</td><td>editable</td>
 *     </tr>
 *     <tr>
 *         <td>/</td><td>/</td><td>editable</td>
 *     </tr>
 *     <tr>
 *         <td>/</td><td>/it</td><td>visible</td>
 *     </tr>
 *     <tr>
 *         <td>/</td><td>/it/car</td><td>visible</td>
 *     </tr>
 *     <tr>
 *         <td>/</td><td>/it/igl</td><td>visible</td>
 *     </tr>
 *     <tr>
 *         <td>/</td><td>/fr</td><td>visible</td>
 *     </tr>
 *     <tr>
 *         <td>/</td><td>null</td><td>not visible</td>
 *     </tr>
 *     <tr>
 *         <td>/it</td><td>/</td><td>editable</td>
 *     </tr>
 *     <tr>
 *         <td>/it</td><td>/it</td><td>editable</td>
 *     </tr>
 *     <tr>
 *         <td>/it</td><td>/it/car</td><td>visible</td>
 *     </tr>
 *     <tr>
 *         <td>/it</td><td>/it/igl</td><td>visible</td>
 *     </tr>
 *     <tr>
 *         <td>/it</td><td>/fr</td><td>not visible</td>
 *     </tr>
 *     <tr>
 *         <td>/it</td><td>null</td><td>not visible</td>
 *     </tr>
 *     <tr>
 *         <td>/it/car</td><td>/</td><td>editable</td>
 *     </tr>
 *     <tr>
 *         <td>/it/car</td><td>/it</td><td>editable</td>
 *     </tr>
 *     <tr>
 *         <td>/it/car</td><td>/it/car</td><td>editable</td>
 *     </tr>
 *     <tr>
 *         <td>/it/car</td><td>/it/igl</td><td>not visible</td>
 *     </tr>
 *     <tr>
 *         <td>/it/car</td><td>/fr</td><td>not visible</td>
 *     </tr>
 *     <tr>
 *         <td>/it/car</td><td>null</td><td>not visible</td>
 *     </tr>
 * </table>
 * <p>any object that is not tenanted (that is, its class does not implement {@link HasAtPath the WithApplicationTenancy interface} is accessible by any user (usual permission rules apply).
 * </p>
 */
public interface HasAtPath {

    String getAtPath();

}
