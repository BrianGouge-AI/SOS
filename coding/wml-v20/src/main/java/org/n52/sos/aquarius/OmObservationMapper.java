/**
 * Copyright (C) 2012-2015 Aquatic Informatics
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 as published
 * by the Free Software Foundation.
 *
 * If the program is linked with libraries which are licensed under one of
 * the following licenses, the combination of the program with the linked
 * library is not considered a "derivative work" of the program:
 *
 *     - Apache License, version 2.0
 *     - Apache Software License, version 1.0
 *     - GNU Lesser General Public License, version 3
 *     - Mozilla Public License, versions 1.0, 1.1 and 2.0
 *     - Common Development and Distribution License (CDDL), version 1.0
 *
 * Therefore the distribution of the program linked with libraries licensed
 * under the aforementioned licenses, is permitted by the copyright holders
 * if the distribution is compliant with both the GNU General Public
 * License version 2 and the aforementioned licenses.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General
 * Public License for more details.
 */
package org.n52.sos.aquarius;

import org.n52.sos.ogc.om.OmObservation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OmObservationMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(OmObservationMapper.class);
    private static final String SEPARATOR_CHARACTER = "_";
    private static final String DEFAULT_AQUARIUS_INTERPOLATION_TYPE = InterpolationTypeMapper.INSTANTANEOUS;
    private static final String BASE_LOGGER_INTERPOLATION_MAPPING_WARNING =
            "Could not determine aquarius interpolation type";
    private final InterpolationTypeMapper interpolationTypeMapper;

    public OmObservationMapper(InterpolationTypeMapper interpolationTypeMapper) {
        this.interpolationTypeMapper = interpolationTypeMapper;
    }

    public String toInterpolationTypeTitle(OmObservation observation) {
        String wml2InterpolationTitle = interpolationTypeMapper.toTitle(DEFAULT_AQUARIUS_INTERPOLATION_TYPE);

        if (null != observation && null != observation.getObservationConstellation() &&
                null != observation.getObservationConstellation().getProcedure()) {

            final String procedureIdentifier = observation.getObservationConstellation().getProcedure().getIdentifier();
            final String title = interpolationTypeMapper.toTitle(
                    mapProcedureIdentifierToInterpolationType(procedureIdentifier));
            if (null != title) {
                wml2InterpolationTitle = title;
            } else {
                LOGGER.info(BASE_LOGGER_INTERPOLATION_MAPPING_WARNING +
                        ": invalid procedure identifier {}", procedureIdentifier);
            }
        } else {
            LOGGER.info(BASE_LOGGER_INTERPOLATION_MAPPING_WARNING + ": invalid observation");
        }

        return wml2InterpolationTitle;
    }

    public String toInterpolationTypeHref(final OmObservation observation) {
        String wml2InterpolationHref = interpolationTypeMapper.toHref(DEFAULT_AQUARIUS_INTERPOLATION_TYPE);

        if (null != observation && null != observation.getObservationConstellation() &&
                null != observation.getObservationConstellation().getProcedure()) {

            final String procedureIdentifier = observation.getObservationConstellation().getProcedure().getIdentifier();
            final String href = interpolationTypeMapper.toHref(
                    mapProcedureIdentifierToInterpolationType(procedureIdentifier));
            if (null != href) {
                wml2InterpolationHref = href;
            } else {
                LOGGER.info(BASE_LOGGER_INTERPOLATION_MAPPING_WARNING +
                        ": invalid procedure identifier {}", procedureIdentifier);
            }
        } else {
            LOGGER.info(BASE_LOGGER_INTERPOLATION_MAPPING_WARNING + ": invalid observation");
        }
        return wml2InterpolationHref;
    }

    private String mapProcedureIdentifierToInterpolationType(String procedureIdentifier) {
        String aquariusInterpolationType = null;
        if (null != procedureIdentifier && !procedureIdentifier.isEmpty()) {
            int indexOfSeparationCharacter = procedureIdentifier.lastIndexOf(SEPARATOR_CHARACTER);
            if ((indexOfSeparationCharacter > 0) & (indexOfSeparationCharacter < (procedureIdentifier.length() - 1))) {
                aquariusInterpolationType = procedureIdentifier.substring(indexOfSeparationCharacter + 1);
            } else {
                LOGGER.info(BASE_LOGGER_INTERPOLATION_MAPPING_WARNING +
                        ": invalid procedure identifier {}", procedureIdentifier);
            }
        } else {
            LOGGER.info(BASE_LOGGER_INTERPOLATION_MAPPING_WARNING + ": procedure identifier is null/empty.");
        }
        return aquariusInterpolationType;
    }

}
