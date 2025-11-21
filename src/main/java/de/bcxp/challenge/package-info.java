/**
 * The root package for the data challenge containing the entry point
 * {@link de.bcxp.challenge.App} and containing the resource files.
 * 
 * Sub-packages are structured by responsibility and domain:
 *
 * - {@code de.bcxp.challenge.io}
 *   Contains abstractions and implementations for reading tabular data
 *   (e.g. {@link de.bcxp.challenge.io.TableReader} and CSV readers).
 *
 * - {@code de.bcxp.challenge.weather}
 *   Contains domain and analysis logic for the weather dataset.
 *
 * - {@code de.bcxp.challenge.countries}
 *   Contains domain and analysis logic for the countries dataset.
 */
package de.bcxp.challenge;