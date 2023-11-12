import { useAppDispatch } from 'app/config/store';
import React, { useState } from 'react';
import { Translate } from 'react-jhipster';
import { useNavigate } from 'react-router-dom';
import { Button, Col, Form, FormGroup, FormText, Input, Label, Row } from 'reactstrap';
import { updateExcelEntity } from './daily-calls.reducer';

export const DailyCallsUpload = () => {
  const dispatch = useAppDispatch();
  const navigate = useNavigate();

  const [file, setFile] = useState<any>();
  const handleSubmit = (e: any) => {};

  const uploadExcel = () => {
    dispatch(updateExcelEntity(file));
  };

  const handleFileChange = event => {
    // Get the selected file from the input
    // uploadExcel(event.target.files[0]);
  };

  const handleUpload = () => {
    // Check if a file is selected
    // if (!selectedFile) {
    //   console.error('No file selected');
    //   return;
    // }
    // // Create a FormData object to send the file
    // const formData = new FormData();
    // formData.append('file', selectedFile);
  };

  return (
    <Row>
      <Col md="8">
        <h2 data-cy="dailyCallsUploadExcel">
          <Translate contentKey="dashboardApp.dailyCalls.upload.title">DailyCalls Upload data from Excel</Translate>
        </h2>

        <div className="d-flex">
          <Form encType="multipart/form">
            <FormGroup>
              <Label for="exampleFile">DailyCalls Excel</Label>
              <Input id="exampleFile" name="file" type="file" onChange={handleFileChange} />
              <FormText>
                <Translate contentKey="dashboardApp.dailyCalls.upload.infoText">Upload the excel file with the Daily Calls data</Translate>
              </FormText>

              <Button onClick={handleUpload}>Upload File</Button>
            </FormGroup>
          </Form>
        </div>
      </Col>
    </Row>
  );
};

export default DailyCallsUpload;
