import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import DailyCalls from './daily-calls';
import DailyCallsDetail from './daily-calls-detail';
import DailyCallsUpdate from './daily-calls-update';
import DailyCallsDeleteDialog from './daily-calls-delete-dialog';
import DailyCallsUploadDialog from './daily-calls-upload-dialog';

const DailyCallsRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<DailyCalls />} />
    <Route path="new" element={<DailyCallsUpdate />} />
    <Route path=":id">
      <Route index element={<DailyCallsDetail />} />
      <Route path="edit" element={<DailyCallsUpdate />} />
      <Route path="delete" element={<DailyCallsDeleteDialog />} />
      <Route path="upload" element={<DailyCallsUploadDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default DailyCallsRoutes;
