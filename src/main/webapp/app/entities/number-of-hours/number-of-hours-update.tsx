import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { INumberOfHours } from 'app/shared/model/number-of-hours.model';
import { getEntity, updateEntity, createEntity, reset } from './number-of-hours.reducer';

export const NumberOfHoursUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const numberOfHoursEntity = useAppSelector(state => state.numberOfHours.entity);
  const loading = useAppSelector(state => state.numberOfHours.loading);
  const updating = useAppSelector(state => state.numberOfHours.updating);
  const updateSuccess = useAppSelector(state => state.numberOfHours.updateSuccess);

  const handleClose = () => {
    navigate('/number-of-hours');
  };

  useEffect(() => {
    if (!isNew) {
      dispatch(getEntity(id));
    }
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    const entity = {
      ...numberOfHoursEntity,
      ...values,
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {}
      : {
          ...numberOfHoursEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="dashboardApp.numberOfHours.home.createOrEditLabel" data-cy="NumberOfHoursCreateUpdateHeading">
            <Translate contentKey="dashboardApp.numberOfHours.home.createOrEditLabel">Create or edit a NumberOfHours</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="number-of-hours-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('dashboardApp.numberOfHours.month')}
                id="number-of-hours-month"
                name="month"
                data-cy="month"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('dashboardApp.numberOfHours.externalAgentDailyHoursAvg')}
                id="number-of-hours-externalAgentDailyHoursAvg"
                name="externalAgentDailyHoursAvg"
                data-cy="externalAgentDailyHoursAvg"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('dashboardApp.numberOfHours.dailyHourAvg')}
                id="number-of-hours-dailyHourAvg"
                name="dailyHourAvg"
                data-cy="dailyHourAvg"
                type="text"
              />
              <ValidatedField
                label={translate('dashboardApp.numberOfHours.avgHoursToAnswerCalls')}
                id="number-of-hours-avgHoursToAnswerCalls"
                name="avgHoursToAnswerCalls"
                data-cy="avgHoursToAnswerCalls"
                type="text"
              />
              <ValidatedField
                label={translate('dashboardApp.numberOfHours.totalHoursToAnswerCalls')}
                id="number-of-hours-totalHoursToAnswerCalls"
                name="totalHoursToAnswerCalls"
                data-cy="totalHoursToAnswerCalls"
                type="text"
              />
              <ValidatedField
                label={translate('dashboardApp.numberOfHours.totalReceivedCalls')}
                id="number-of-hours-totalReceivedCalls"
                name="totalReceivedCalls"
                data-cy="totalReceivedCalls"
                type="text"
              />
              <ValidatedField
                label={translate('dashboardApp.numberOfHours.totalAttendedCalls')}
                id="number-of-hours-totalAttendedCalls"
                name="totalAttendedCalls"
                data-cy="totalAttendedCalls"
                type="text"
              />
              <ValidatedField
                label={translate('dashboardApp.numberOfHours.attendedCallsPercentage')}
                id="number-of-hours-attendedCallsPercentage"
                name="attendedCallsPercentage"
                data-cy="attendedCallsPercentage"
                type="text"
              />
              <ValidatedField
                label={translate('dashboardApp.numberOfHours.avgDailyAttendedCalls')}
                id="number-of-hours-avgDailyAttendedCalls"
                name="avgDailyAttendedCalls"
                data-cy="avgDailyAttendedCalls"
                type="text"
              />
              <ValidatedField
                label={translate('dashboardApp.numberOfHours.avgDailyAttendedCallsByExternal')}
                id="number-of-hours-avgDailyAttendedCallsByExternal"
                name="avgDailyAttendedCallsByExternal"
                data-cy="avgDailyAttendedCallsByExternal"
                type="text"
              />
              <ValidatedField
                label={translate('dashboardApp.numberOfHours.avgDailyAttendedCallsByExternalByDay')}
                id="number-of-hours-avgDailyAttendedCallsByExternalByDay"
                name="avgDailyAttendedCallsByExternalByDay"
                data-cy="avgDailyAttendedCallsByExternalByDay"
                type="text"
              />
              <ValidatedField
                label={translate('dashboardApp.numberOfHours.avgDailyAttendedCallsByInternal')}
                id="number-of-hours-avgDailyAttendedCallsByInternal"
                name="avgDailyAttendedCallsByInternal"
                data-cy="avgDailyAttendedCallsByInternal"
                type="text"
              />
              <ValidatedField
                label={translate('dashboardApp.numberOfHours.totalReceivedChats')}
                id="number-of-hours-totalReceivedChats"
                name="totalReceivedChats"
                data-cy="totalReceivedChats"
                type="text"
              />
              <ValidatedField
                label={translate('dashboardApp.numberOfHours.totalAttendedChats')}
                id="number-of-hours-totalAttendedChats"
                name="totalAttendedChats"
                data-cy="totalAttendedChats"
                type="text"
              />
              <ValidatedField
                label={translate('dashboardApp.numberOfHours.totalReceivedMails')}
                id="number-of-hours-totalReceivedMails"
                name="totalReceivedMails"
                data-cy="totalReceivedMails"
                type="text"
              />
              <ValidatedField
                label={translate('dashboardApp.numberOfHours.totalAttendedMails')}
                id="number-of-hours-totalAttendedMails"
                name="totalAttendedMails"
                data-cy="totalAttendedMails"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/number-of-hours" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default NumberOfHoursUpdate;
