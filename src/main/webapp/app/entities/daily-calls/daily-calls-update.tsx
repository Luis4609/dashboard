import React, { useState, useEffect } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { IDailyCalls } from 'app/shared/model/daily-calls.model';
import { getEntity, updateEntity, createEntity, reset } from './daily-calls.reducer';

export const DailyCallsUpdate = () => {
  const dispatch = useAppDispatch();

  const navigate = useNavigate();

  const { id } = useParams<'id'>();
  const isNew = id === undefined;

  const dailyCallsEntity = useAppSelector(state => state.dailyCalls.entity);
  const loading = useAppSelector(state => state.dailyCalls.loading);
  const updating = useAppSelector(state => state.dailyCalls.updating);
  const updateSuccess = useAppSelector(state => state.dailyCalls.updateSuccess);

  const handleClose = () => {
    navigate('/daily-calls' + location.search);
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
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
      ...dailyCallsEntity,
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
          ...dailyCallsEntity,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="dashboardApp.dailyCalls.home.createOrEditLabel" data-cy="DailyCallsCreateUpdateHeading">
            <Translate contentKey="dashboardApp.dailyCalls.home.createOrEditLabel">Create or edit a DailyCalls</Translate>
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
                  id="daily-calls-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('dashboardApp.dailyCalls.day')}
                id="daily-calls-day"
                name="day"
                data-cy="day"
                type="date"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                }}
              />
              <ValidatedField
                label={translate('dashboardApp.dailyCalls.totalDailyReceivedCalls')}
                id="daily-calls-totalDailyReceivedCalls"
                name="totalDailyReceivedCalls"
                data-cy="totalDailyReceivedCalls"
                type="text"
              />
              <ValidatedField
                label={translate('dashboardApp.dailyCalls.totalDailyAttendedCalls')}
                id="daily-calls-totalDailyAttendedCalls"
                name="totalDailyAttendedCalls"
                data-cy="totalDailyAttendedCalls"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('dashboardApp.dailyCalls.totalDailyMissedCalls')}
                id="daily-calls-totalDailyMissedCalls"
                name="totalDailyMissedCalls"
                data-cy="totalDailyMissedCalls"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('dashboardApp.dailyCalls.totalDailyReceivedCallsExternalAgent')}
                id="daily-calls-totalDailyReceivedCallsExternalAgent"
                name="totalDailyReceivedCallsExternalAgent"
                data-cy="totalDailyReceivedCallsExternalAgent"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('dashboardApp.dailyCalls.totalDailyAttendedCallsExternalAgent')}
                id="daily-calls-totalDailyAttendedCallsExternalAgent"
                name="totalDailyAttendedCallsExternalAgent"
                data-cy="totalDailyAttendedCallsExternalAgent"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('dashboardApp.dailyCalls.totalDailyReceivedCallsInternalAgent')}
                id="daily-calls-totalDailyReceivedCallsInternalAgent"
                name="totalDailyReceivedCallsInternalAgent"
                data-cy="totalDailyReceivedCallsInternalAgent"
                type="text"
              />
              <ValidatedField
                label={translate('dashboardApp.dailyCalls.totalDailyAttendedCallsInternalAgent')}
                id="daily-calls-totalDailyAttendedCallsInternalAgent"
                name="totalDailyAttendedCallsInternalAgent"
                data-cy="totalDailyAttendedCallsInternalAgent"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('dashboardApp.dailyCalls.totalDailyCallsTimeInMin')}
                id="daily-calls-totalDailyCallsTimeInMin"
                name="totalDailyCallsTimeInMin"
                data-cy="totalDailyCallsTimeInMin"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('dashboardApp.dailyCalls.totalDailyCallsTimeExternalAgentInMin')}
                id="daily-calls-totalDailyCallsTimeExternalAgentInMin"
                name="totalDailyCallsTimeExternalAgentInMin"
                data-cy="totalDailyCallsTimeExternalAgentInMin"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('dashboardApp.dailyCalls.totalDailyCallsTimeInternalAgentInMin')}
                id="daily-calls-totalDailyCallsTimeInternalAgentInMin"
                name="totalDailyCallsTimeInternalAgentInMin"
                data-cy="totalDailyCallsTimeInternalAgentInMin"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('dashboardApp.dailyCalls.avgDailyOperationTimeExternalAgentInMin')}
                id="daily-calls-avgDailyOperationTimeExternalAgentInMin"
                name="avgDailyOperationTimeExternalAgentInMin"
                data-cy="avgDailyOperationTimeExternalAgentInMin"
                type="text"
                validate={{
                  required: { value: true, message: translate('entity.validation.required') },
                  validate: v => isNumber(v) || translate('entity.validation.number'),
                }}
              />
              <ValidatedField
                label={translate('dashboardApp.dailyCalls.avgDailyOperationTimeInternalAgentInMin')}
                id="daily-calls-avgDailyOperationTimeInternalAgentInMin"
                name="avgDailyOperationTimeInternalAgentInMin"
                data-cy="avgDailyOperationTimeInternalAgentInMin"
                type="text"
              />
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/daily-calls" replace color="info">
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

export default DailyCallsUpdate;
