/**
 * Copyright (C) 2012-2015 52°North Initiative for Geospatial Open Source
 * Software GmbH
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

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;
import org.n52.sos.ogc.om.OmObservation;
import org.n52.sos.ogc.om.OmObservationConstellation;
import org.n52.sos.ogc.wml.ObservationProcess;

public class OmObservationMapperTest {

    @Test
    public void toInterpolationTypeHref_withObservationWithProcedureIdentifier_returnsExpectedHref() {

        String procedureIdentifier = "abc123_InstantTotal";
        String expectedHref = "http://www.opengis.net/def/waterml/2.0/interpolationType/InstantTotal";

        ObservationProcess observationProcess = new ObservationProcess();
        observationProcess.setIdentifier(procedureIdentifier);
        OmObservationConstellation omObservationConstellation = new OmObservationConstellation();
        omObservationConstellation.setProcedure(observationProcess);
        OmObservation observation = new OmObservation();
        observation.setObservationConstellation(omObservationConstellation);

        InterpolationTypeMapper interpolationTypeMapperMock =
                createMockInterpolationTypeMapper(expectedHref, null);

        OmObservationMapper omObservationMapper = new OmObservationMapper(interpolationTypeMapperMock);
        String href = omObservationMapper.toInterpolationTypeHref(observation);

        Mockito.verify(interpolationTypeMapperMock).toHref("InstantTotal");
        Assert.assertEquals(expectedHref, href);
    }

    @Test
    public void toInterpolationTypeHref_withObservationWithInvalidProcedureIdentifier_returnsDefaultHref() {

        String procedureIdentifier = "InstantTotal";
        String expectedHref = "http://www.opengis.net/def/waterml/2.0/interpolationType/Continuous";

        ObservationProcess observationProcess = new ObservationProcess();
        observationProcess.setIdentifier(procedureIdentifier);
        OmObservationConstellation omObservationConstellation = new OmObservationConstellation();
        omObservationConstellation.setProcedure(observationProcess);
        OmObservation observation = new OmObservation();
        observation.setObservationConstellation(omObservationConstellation);

        InterpolationTypeMapper interpolationTypeMapperMock =
                createMockInterpolationTypeMapper(expectedHref, null);

        OmObservationMapper omObservationMapper = new OmObservationMapper(interpolationTypeMapperMock);
        String href = omObservationMapper.toInterpolationTypeHref(observation);

        Mockito.verify(interpolationTypeMapperMock).toHref(null);
        Assert.assertEquals(expectedHref, href);
    }

    @Test
    public void toInterpolationTypeHref_withProcedureIdentifierWithMultipleUnderscores_returnsDefaultHref() {

        String procedureIdentifier = "abc_123_InstantTotal";
        String expectedHref = "http://www.opengis.net/def/waterml/2.0/interpolationType/InstantTotal";

        ObservationProcess observationProcess = new ObservationProcess();
        observationProcess.setIdentifier(procedureIdentifier);
        OmObservationConstellation omObservationConstellation = new OmObservationConstellation();
        omObservationConstellation.setProcedure(observationProcess);
        OmObservation observation = new OmObservation();
        observation.setObservationConstellation(omObservationConstellation);

        InterpolationTypeMapper interpolationTypeMapperMock =
                createMockInterpolationTypeMapper(expectedHref, null);

        OmObservationMapper omObservationMapper = new OmObservationMapper(interpolationTypeMapperMock);
        String href = omObservationMapper.toInterpolationTypeHref(observation);

        Mockito.verify(interpolationTypeMapperMock).toHref("InstantTotal");
        Assert.assertEquals(expectedHref, href);
    }

    @Test
    public void toInterpolationTypeTitle_withObservationWithProcedureIdentifier_returnsExpectedTitle() {

        String procedureIdentifier = "abc123_InstantTotal";
        String expectedTitle = "InstantTotal";

        ObservationProcess observationProcess = new ObservationProcess();
        observationProcess.setIdentifier(procedureIdentifier);
        OmObservationConstellation omObservationConstellation = new OmObservationConstellation();
        omObservationConstellation.setProcedure(observationProcess);
        OmObservation observation = new OmObservation();
        observation.setObservationConstellation(omObservationConstellation);

        InterpolationTypeMapper interpolationTypeMapperMock =
                createMockInterpolationTypeMapper(null, expectedTitle);

        OmObservationMapper omObservationMapper = new OmObservationMapper(interpolationTypeMapperMock);
        String title = omObservationMapper.toInterpolationTypeTitle(observation);

        Mockito.verify(interpolationTypeMapperMock).toTitle("InstantTotal");
        Assert.assertEquals(expectedTitle, title);
    }

    private InterpolationTypeMapper createMockInterpolationTypeMapper(String expectedHref, String expectedTitle) {
        InterpolationTypeMapper interpolationTypeMapperMock = Mockito.mock(InterpolationTypeMapper.class);
        Mockito.when(interpolationTypeMapperMock.toHref(Mockito.anyString()))
                .thenReturn(expectedHref);
        Mockito.when(interpolationTypeMapperMock.toTitle(Mockito.anyString()))
                .thenReturn(expectedTitle);
        return interpolationTypeMapperMock;
    }

}
