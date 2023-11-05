import React, { useState, useEffect } from 'react';
import InfiniteScroll from 'react-infinite-scroll-component';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, getPaginationState } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSort, faSortUp, faSortDown } from '@fortawesome/free-solid-svg-icons';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/shared/util/pagination.constants';
import { overridePaginationStateWithQueryParams } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

import { getEntities, reset } from './number-of-hours.reducer';

export const NumberOfHours = () => {
  const dispatch = useAppDispatch();

  const pageLocation = useLocation();

  const [paginationState, setPaginationState] = useState(
    overridePaginationStateWithQueryParams(getPaginationState(pageLocation, ITEMS_PER_PAGE, 'id'), pageLocation.search),
  );
  const [sorting, setSorting] = useState(false);

  const numberOfHoursList = useAppSelector(state => state.numberOfHours.entities);
  const loading = useAppSelector(state => state.numberOfHours.loading);
  const links = useAppSelector(state => state.numberOfHours.links);
  const updateSuccess = useAppSelector(state => state.numberOfHours.updateSuccess);

  const getAllEntities = () => {
    dispatch(
      getEntities({
        page: paginationState.activePage - 1,
        size: paginationState.itemsPerPage,
        sort: `${paginationState.sort},${paginationState.order}`,
      }),
    );
  };

  const resetAll = () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
    });
    dispatch(getEntities({}));
  };

  useEffect(() => {
    resetAll();
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      resetAll();
    }
  }, [updateSuccess]);

  useEffect(() => {
    getAllEntities();
  }, [paginationState.activePage]);

  const handleLoadMore = () => {
    if ((window as any).pageYOffset > 0) {
      setPaginationState({
        ...paginationState,
        activePage: paginationState.activePage + 1,
      });
    }
  };

  useEffect(() => {
    if (sorting) {
      getAllEntities();
      setSorting(false);
    }
  }, [sorting]);

  const sort = p => () => {
    dispatch(reset());
    setPaginationState({
      ...paginationState,
      activePage: 1,
      order: paginationState.order === ASC ? DESC : ASC,
      sort: p,
    });
    setSorting(true);
  };

  const handleSyncList = () => {
    resetAll();
  };

  const getSortIconByFieldName = (fieldName: string) => {
    const sortFieldName = paginationState.sort;
    const order = paginationState.order;
    if (sortFieldName !== fieldName) {
      return faSort;
    } else {
      return order === ASC ? faSortUp : faSortDown;
    }
  };

  return (
    <div>
      <h2 id="number-of-hours-heading" data-cy="NumberOfHoursHeading">
        <Translate contentKey="dashboardApp.numberOfHours.home.title">Number Of Hours</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="dashboardApp.numberOfHours.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to="/number-of-hours/new" className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="dashboardApp.numberOfHours.home.createLabel">Create new Number Of Hours</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        <InfiniteScroll
          dataLength={numberOfHoursList ? numberOfHoursList.length : 0}
          next={handleLoadMore}
          hasMore={paginationState.activePage - 1 < links.next}
          loader={<div className="loader">Loading ...</div>}
        >
          {numberOfHoursList && numberOfHoursList.length > 0 ? (
            <Table responsive>
              <thead>
                <tr>
                  <th className="hand" onClick={sort('id')}>
                    <Translate contentKey="dashboardApp.numberOfHours.id">ID</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('id')} />
                  </th>
                  <th className="hand" onClick={sort('month')}>
                    <Translate contentKey="dashboardApp.numberOfHours.month">Month</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('month')} />
                  </th>
                  <th className="hand" onClick={sort('externalAgentDailyHoursAvg')}>
                    <Translate contentKey="dashboardApp.numberOfHours.externalAgentDailyHoursAvg">External Agent Daily Hours Avg</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('externalAgentDailyHoursAvg')} />
                  </th>
                  <th className="hand" onClick={sort('dailyHourAvg')}>
                    <Translate contentKey="dashboardApp.numberOfHours.dailyHourAvg">Daily Hour Avg</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('dailyHourAvg')} />
                  </th>
                  <th className="hand" onClick={sort('avgHoursToAnswerCalls')}>
                    <Translate contentKey="dashboardApp.numberOfHours.avgHoursToAnswerCalls">Avg Hours To Answer Calls</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('avgHoursToAnswerCalls')} />
                  </th>
                  <th className="hand" onClick={sort('totalHoursToAnswerCalls')}>
                    <Translate contentKey="dashboardApp.numberOfHours.totalHoursToAnswerCalls">Total Hours To Answer Calls</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('totalHoursToAnswerCalls')} />
                  </th>
                  <th className="hand" onClick={sort('totalReceivedCalls')}>
                    <Translate contentKey="dashboardApp.numberOfHours.totalReceivedCalls">Total Received Calls</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('totalReceivedCalls')} />
                  </th>
                  <th className="hand" onClick={sort('totalAttendedCalls')}>
                    <Translate contentKey="dashboardApp.numberOfHours.totalAttendedCalls">Total Attended Calls</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('totalAttendedCalls')} />
                  </th>
                  <th className="hand" onClick={sort('attendedCallsPercentage')}>
                    <Translate contentKey="dashboardApp.numberOfHours.attendedCallsPercentage">Attended Calls Percentage</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('attendedCallsPercentage')} />
                  </th>
                  <th className="hand" onClick={sort('avgDailyAttendedCalls')}>
                    <Translate contentKey="dashboardApp.numberOfHours.avgDailyAttendedCalls">Avg Daily Attended Calls</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('avgDailyAttendedCalls')} />
                  </th>
                  <th className="hand" onClick={sort('avgDailyAttendedCallsByExternal')}>
                    <Translate contentKey="dashboardApp.numberOfHours.avgDailyAttendedCallsByExternal">
                      Avg Daily Attended Calls By External
                    </Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('avgDailyAttendedCallsByExternal')} />
                  </th>
                  <th className="hand" onClick={sort('avgDailyAttendedCallsByExternalByDay')}>
                    <Translate contentKey="dashboardApp.numberOfHours.avgDailyAttendedCallsByExternalByDay">
                      Avg Daily Attended Calls By External By Day
                    </Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('avgDailyAttendedCallsByExternalByDay')} />
                  </th>
                  <th className="hand" onClick={sort('avgDailyAttendedCallsByInternal')}>
                    <Translate contentKey="dashboardApp.numberOfHours.avgDailyAttendedCallsByInternal">
                      Avg Daily Attended Calls By Internal
                    </Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('avgDailyAttendedCallsByInternal')} />
                  </th>
                  <th className="hand" onClick={sort('totalReceivedChats')}>
                    <Translate contentKey="dashboardApp.numberOfHours.totalReceivedChats">Total Received Chats</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('totalReceivedChats')} />
                  </th>
                  <th className="hand" onClick={sort('totalAttendedChats')}>
                    <Translate contentKey="dashboardApp.numberOfHours.totalAttendedChats">Total Attended Chats</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('totalAttendedChats')} />
                  </th>
                  <th className="hand" onClick={sort('totalReceivedMails')}>
                    <Translate contentKey="dashboardApp.numberOfHours.totalReceivedMails">Total Received Mails</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('totalReceivedMails')} />
                  </th>
                  <th className="hand" onClick={sort('totalAttendedMails')}>
                    <Translate contentKey="dashboardApp.numberOfHours.totalAttendedMails">Total Attended Mails</Translate>{' '}
                    <FontAwesomeIcon icon={getSortIconByFieldName('totalAttendedMails')} />
                  </th>
                  <th />
                </tr>
              </thead>
              <tbody>
                {numberOfHoursList.map((numberOfHours, i) => (
                  <tr key={`entity-${i}`} data-cy="entityTable">
                    <td>
                      <Button tag={Link} to={`/number-of-hours/${numberOfHours.id}`} color="link" size="sm">
                        {numberOfHours.id}
                      </Button>
                    </td>
                    <td>{numberOfHours.month}</td>
                    <td>{numberOfHours.externalAgentDailyHoursAvg}</td>
                    <td>{numberOfHours.dailyHourAvg}</td>
                    <td>{numberOfHours.avgHoursToAnswerCalls}</td>
                    <td>{numberOfHours.totalHoursToAnswerCalls}</td>
                    <td>{numberOfHours.totalReceivedCalls}</td>
                    <td>{numberOfHours.totalAttendedCalls}</td>
                    <td>{numberOfHours.attendedCallsPercentage}</td>
                    <td>{numberOfHours.avgDailyAttendedCalls}</td>
                    <td>{numberOfHours.avgDailyAttendedCallsByExternal}</td>
                    <td>{numberOfHours.avgDailyAttendedCallsByExternalByDay}</td>
                    <td>{numberOfHours.avgDailyAttendedCallsByInternal}</td>
                    <td>{numberOfHours.totalReceivedChats}</td>
                    <td>{numberOfHours.totalAttendedChats}</td>
                    <td>{numberOfHours.totalReceivedMails}</td>
                    <td>{numberOfHours.totalAttendedMails}</td>
                    <td className="text-end">
                      <div className="btn-group flex-btn-group-container">
                        <Button tag={Link} to={`/number-of-hours/${numberOfHours.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                          <FontAwesomeIcon icon="eye" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.view">View</Translate>
                          </span>
                        </Button>
                        <Button
                          tag={Link}
                          to={`/number-of-hours/${numberOfHours.id}/edit`}
                          color="primary"
                          size="sm"
                          data-cy="entityEditButton"
                        >
                          <FontAwesomeIcon icon="pencil-alt" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.edit">Edit</Translate>
                          </span>
                        </Button>
                        <Button
                          tag={Link}
                          to={`/number-of-hours/${numberOfHours.id}/delete`}
                          color="danger"
                          size="sm"
                          data-cy="entityDeleteButton"
                        >
                          <FontAwesomeIcon icon="trash" />{' '}
                          <span className="d-none d-md-inline">
                            <Translate contentKey="entity.action.delete">Delete</Translate>
                          </span>
                        </Button>
                      </div>
                    </td>
                  </tr>
                ))}
              </tbody>
            </Table>
          ) : (
            !loading && (
              <div className="alert alert-warning">
                <Translate contentKey="dashboardApp.numberOfHours.home.notFound">No Number Of Hours found</Translate>
              </div>
            )
          )}
        </InfiniteScroll>
      </div>
    </div>
  );
};

export default NumberOfHours;
