import axios from 'axios';
import { createAsyncThunk, isFulfilled, isPending } from '@reduxjs/toolkit';
import { loadMoreDataWhenScrolled, parseHeaderForLinks } from 'react-jhipster';
import { cleanEntity } from 'app/shared/util/entity-utils';
import { IQueryParams, createEntitySlice, EntityState, serializeAxiosError } from 'app/shared/reducers/reducer.utils';
import { INumberOfHours, defaultValue } from 'app/shared/model/number-of-hours.model';

const initialState: EntityState<INumberOfHours> = {
  loading: false,
  errorMessage: null,
  entities: [],
  entity: defaultValue,
  links: { next: 0 },
  updating: false,
  totalItems: 0,
  updateSuccess: false,
};

const apiUrl = 'api/number-of-hours';

// Actions

export const getEntities = createAsyncThunk('numberOfHours/fetch_entity_list', async ({ page, size, sort }: IQueryParams) => {
  const requestUrl = `${apiUrl}?${sort ? `page=${page}&size=${size}&sort=${sort}&` : ''}cacheBuster=${new Date().getTime()}`;
  return axios.get<INumberOfHours[]>(requestUrl);
});

export const getEntity = createAsyncThunk(
  'numberOfHours/fetch_entity',
  async (id: string | number) => {
    const requestUrl = `${apiUrl}/${id}`;
    return axios.get<INumberOfHours>(requestUrl);
  },
  { serializeError: serializeAxiosError },
);

export const createEntity = createAsyncThunk(
  'numberOfHours/create_entity',
  async (entity: INumberOfHours, thunkAPI) => {
    return axios.post<INumberOfHours>(apiUrl, cleanEntity(entity));
  },
  { serializeError: serializeAxiosError },
);

export const updateEntity = createAsyncThunk(
  'numberOfHours/update_entity',
  async (entity: INumberOfHours, thunkAPI) => {
    return axios.put<INumberOfHours>(`${apiUrl}/${entity.id}`, cleanEntity(entity));
  },
  { serializeError: serializeAxiosError },
);

export const partialUpdateEntity = createAsyncThunk(
  'numberOfHours/partial_update_entity',
  async (entity: INumberOfHours, thunkAPI) => {
    return axios.patch<INumberOfHours>(`${apiUrl}/${entity.id}`, cleanEntity(entity));
  },
  { serializeError: serializeAxiosError },
);

export const deleteEntity = createAsyncThunk(
  'numberOfHours/delete_entity',
  async (id: string | number, thunkAPI) => {
    const requestUrl = `${apiUrl}/${id}`;
    return await axios.delete<INumberOfHours>(requestUrl);
  },
  { serializeError: serializeAxiosError },
);

// slice

export const NumberOfHoursSlice = createEntitySlice({
  name: 'numberOfHours',
  initialState,
  extraReducers(builder) {
    builder
      .addCase(getEntity.fulfilled, (state, action) => {
        state.loading = false;
        state.entity = action.payload.data;
      })
      .addCase(deleteEntity.fulfilled, state => {
        state.updating = false;
        state.updateSuccess = true;
        state.entity = {};
      })
      .addMatcher(isFulfilled(getEntities), (state, action) => {
        const { data, headers } = action.payload;
        const links = parseHeaderForLinks(headers.link);

        return {
          ...state,
          loading: false,
          links,
          entities: loadMoreDataWhenScrolled(state.entities, data, links),
          totalItems: parseInt(headers['x-total-count'], 10),
        };
      })
      .addMatcher(isFulfilled(createEntity, updateEntity, partialUpdateEntity), (state, action) => {
        state.updating = false;
        state.loading = false;
        state.updateSuccess = true;
        state.entity = action.payload.data;
      })
      .addMatcher(isPending(getEntities, getEntity), state => {
        state.errorMessage = null;
        state.updateSuccess = false;
        state.loading = true;
      })
      .addMatcher(isPending(createEntity, updateEntity, partialUpdateEntity, deleteEntity), state => {
        state.errorMessage = null;
        state.updateSuccess = false;
        state.updating = true;
      });
  },
});

export const { reset } = NumberOfHoursSlice.actions;

// Reducer
export default NumberOfHoursSlice.reducer;
