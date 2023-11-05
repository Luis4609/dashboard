import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import NumberOfHours from './number-of-hours';
import NumberOfHoursDetail from './number-of-hours-detail';
import NumberOfHoursUpdate from './number-of-hours-update';
import NumberOfHoursDeleteDialog from './number-of-hours-delete-dialog';

const NumberOfHoursRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<NumberOfHours />} />
    <Route path="new" element={<NumberOfHoursUpdate />} />
    <Route path=":id">
      <Route index element={<NumberOfHoursDetail />} />
      <Route path="edit" element={<NumberOfHoursUpdate />} />
      <Route path="delete" element={<NumberOfHoursDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default NumberOfHoursRoutes;
