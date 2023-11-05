import React, { useEffect } from 'react';
import { Link, useParams } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntity } from './number-of-hours.reducer';

export const NumberOfHoursDetail = () => {
  const dispatch = useAppDispatch();

  const { id } = useParams<'id'>();

  useEffect(() => {
    dispatch(getEntity(id));
  }, []);

  const numberOfHoursEntity = useAppSelector(state => state.numberOfHours.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="numberOfHoursDetailsHeading">
          <Translate contentKey="dashboardApp.numberOfHours.detail.title">NumberOfHours</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{numberOfHoursEntity.id}</dd>
          <dt>
            <span id="month">
              <Translate contentKey="dashboardApp.numberOfHours.month">Month</Translate>
            </span>
          </dt>
          <dd>{numberOfHoursEntity.month}</dd>
          <dt>
            <span id="externalAgentDailyHoursAvg">
              <Translate contentKey="dashboardApp.numberOfHours.externalAgentDailyHoursAvg">External Agent Daily Hours Avg</Translate>
            </span>
          </dt>
          <dd>{numberOfHoursEntity.externalAgentDailyHoursAvg}</dd>
          <dt>
            <span id="dailyHourAvg">
              <Translate contentKey="dashboardApp.numberOfHours.dailyHourAvg">Daily Hour Avg</Translate>
            </span>
          </dt>
          <dd>{numberOfHoursEntity.dailyHourAvg}</dd>
          <dt>
            <span id="avgHoursToAnswerCalls">
              <Translate contentKey="dashboardApp.numberOfHours.avgHoursToAnswerCalls">Avg Hours To Answer Calls</Translate>
            </span>
          </dt>
          <dd>{numberOfHoursEntity.avgHoursToAnswerCalls}</dd>
          <dt>
            <span id="totalHoursToAnswerCalls">
              <Translate contentKey="dashboardApp.numberOfHours.totalHoursToAnswerCalls">Total Hours To Answer Calls</Translate>
            </span>
          </dt>
          <dd>{numberOfHoursEntity.totalHoursToAnswerCalls}</dd>
          <dt>
            <span id="totalReceivedCalls">
              <Translate contentKey="dashboardApp.numberOfHours.totalReceivedCalls">Total Received Calls</Translate>
            </span>
          </dt>
          <dd>{numberOfHoursEntity.totalReceivedCalls}</dd>
          <dt>
            <span id="totalAttendedCalls">
              <Translate contentKey="dashboardApp.numberOfHours.totalAttendedCalls">Total Attended Calls</Translate>
            </span>
          </dt>
          <dd>{numberOfHoursEntity.totalAttendedCalls}</dd>
          <dt>
            <span id="attendedCallsPercentage">
              <Translate contentKey="dashboardApp.numberOfHours.attendedCallsPercentage">Attended Calls Percentage</Translate>
            </span>
          </dt>
          <dd>{numberOfHoursEntity.attendedCallsPercentage}</dd>
          <dt>
            <span id="avgDailyAttendedCalls">
              <Translate contentKey="dashboardApp.numberOfHours.avgDailyAttendedCalls">Avg Daily Attended Calls</Translate>
            </span>
          </dt>
          <dd>{numberOfHoursEntity.avgDailyAttendedCalls}</dd>
          <dt>
            <span id="avgDailyAttendedCallsByExternal">
              <Translate contentKey="dashboardApp.numberOfHours.avgDailyAttendedCallsByExternal">
                Avg Daily Attended Calls By External
              </Translate>
            </span>
          </dt>
          <dd>{numberOfHoursEntity.avgDailyAttendedCallsByExternal}</dd>
          <dt>
            <span id="avgDailyAttendedCallsByExternalByDay">
              <Translate contentKey="dashboardApp.numberOfHours.avgDailyAttendedCallsByExternalByDay">
                Avg Daily Attended Calls By External By Day
              </Translate>
            </span>
          </dt>
          <dd>{numberOfHoursEntity.avgDailyAttendedCallsByExternalByDay}</dd>
          <dt>
            <span id="avgDailyAttendedCallsByInternal">
              <Translate contentKey="dashboardApp.numberOfHours.avgDailyAttendedCallsByInternal">
                Avg Daily Attended Calls By Internal
              </Translate>
            </span>
          </dt>
          <dd>{numberOfHoursEntity.avgDailyAttendedCallsByInternal}</dd>
          <dt>
            <span id="totalReceivedChats">
              <Translate contentKey="dashboardApp.numberOfHours.totalReceivedChats">Total Received Chats</Translate>
            </span>
          </dt>
          <dd>{numberOfHoursEntity.totalReceivedChats}</dd>
          <dt>
            <span id="totalAttendedChats">
              <Translate contentKey="dashboardApp.numberOfHours.totalAttendedChats">Total Attended Chats</Translate>
            </span>
          </dt>
          <dd>{numberOfHoursEntity.totalAttendedChats}</dd>
          <dt>
            <span id="totalReceivedMails">
              <Translate contentKey="dashboardApp.numberOfHours.totalReceivedMails">Total Received Mails</Translate>
            </span>
          </dt>
          <dd>{numberOfHoursEntity.totalReceivedMails}</dd>
          <dt>
            <span id="totalAttendedMails">
              <Translate contentKey="dashboardApp.numberOfHours.totalAttendedMails">Total Attended Mails</Translate>
            </span>
          </dt>
          <dd>{numberOfHoursEntity.totalAttendedMails}</dd>
        </dl>
        <Button tag={Link} to="/number-of-hours" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/number-of-hours/${numberOfHoursEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default NumberOfHoursDetail;
