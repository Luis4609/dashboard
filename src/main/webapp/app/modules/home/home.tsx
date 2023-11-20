import './home.scss';

import React from 'react';
import { Link } from 'react-router-dom';
import { Translate } from 'react-jhipster';
import { Row, Col, Alert, Card, CardTitle, CardText, Button, CardHeader, CardBody } from 'reactstrap';

import { useAppSelector } from 'app/config/store';

export const Home = () => {
  const account = useAppSelector(state => state.authentication.account);

  return (
    <>
      <Row>
        <Col md="9">
          <h2>
            <Translate contentKey="home.title">Welcome to the Dashboard!</Translate>
          </h2>
          <p className="lead">
            <Translate contentKey="home.subtitle">This is your homepage</Translate>
          </p>
          {account?.login ? (
            <div>
              <Alert color="success">
                <Translate contentKey="home.logged.message" interpolate={{ username: account.login }}>
                  You are logged in as user {account.login}.
                </Translate>
              </Alert>
            </div>
          ) : (
            <div>
              <Link to="/register" className="btn btn-primary mr-1">
                Log in or Register new account
              </Link>
            </div>
          )}
        </Col>
      </Row>
      <Row>
        <Col>
          <Card
            body
            className="my-2"
            color="secondary"
            inverse
            style={{
              width: '18rem',
            }}
          >
            <CardTitle tag="h5">20861</CardTitle>
            <CardText>Llamadas perdidas</CardText>
            {/* <Button>Go somewhere</Button> */}
          </Card>
        </Col>
        <Col>
          <Card
            body
            className="my-2"
            color="success"
            inverse
            style={{
              width: '18rem',
            }}
          >
            <CardTitle tag="h5">85430</CardTitle>
            <CardText>Llamadas recibidas</CardText>
            {/* <Button>Go somewhere</Button> */}
          </Card>
        </Col>
        <Col>
          <Card
            className="my-2"
            color="primary"
            outline
            style={{
              width: '18rem',
            }}
          >
            <CardHeader>64569</CardHeader>
            <CardBody>
              <CardTitle tag="h5">Llamadas atendidas</CardTitle>
              <CardText>Numero total de llamadas atendidas.</CardText>
            </CardBody>
          </Card>
        </Col>
      </Row>
    </>
  );
};

export default Home;
