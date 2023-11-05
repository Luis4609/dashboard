import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('NumberOfHours e2e test', () => {
  const numberOfHoursPageUrl = '/number-of-hours';
  const numberOfHoursPageUrlPattern = new RegExp('/number-of-hours(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const numberOfHoursSample = { month: 'whereas unexpectedly schmooze', externalAgentDailyHoursAvg: 1456.19 };

  let numberOfHours;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/number-of-hours+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/number-of-hours').as('postEntityRequest');
    cy.intercept('DELETE', '/api/number-of-hours/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (numberOfHours) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/number-of-hours/${numberOfHours.id}`,
      }).then(() => {
        numberOfHours = undefined;
      });
    }
  });

  it('NumberOfHours menu should load NumberOfHours page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('number-of-hours');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('NumberOfHours').should('exist');
    cy.url().should('match', numberOfHoursPageUrlPattern);
  });

  describe('NumberOfHours page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(numberOfHoursPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create NumberOfHours page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/number-of-hours/new$'));
        cy.getEntityCreateUpdateHeading('NumberOfHours');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', numberOfHoursPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/number-of-hours',
          body: numberOfHoursSample,
        }).then(({ body }) => {
          numberOfHours = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/number-of-hours+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/number-of-hours?page=0&size=20>; rel="last",<http://localhost/api/number-of-hours?page=0&size=20>; rel="first"',
              },
              body: [numberOfHours],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(numberOfHoursPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details NumberOfHours page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('numberOfHours');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', numberOfHoursPageUrlPattern);
      });

      it('edit button click should load edit NumberOfHours page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('NumberOfHours');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', numberOfHoursPageUrlPattern);
      });

      it('edit button click should load edit NumberOfHours page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('NumberOfHours');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', numberOfHoursPageUrlPattern);
      });

      it('last delete button click should delete instance of NumberOfHours', () => {
        cy.intercept('GET', '/api/number-of-hours/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('numberOfHours').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', numberOfHoursPageUrlPattern);

        numberOfHours = undefined;
      });
    });
  });

  describe('new NumberOfHours page', () => {
    beforeEach(() => {
      cy.visit(`${numberOfHoursPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('NumberOfHours');
    });

    it('should create an instance of NumberOfHours', () => {
      cy.get(`[data-cy="month"]`).type('certainly symmetry whether');
      cy.get(`[data-cy="month"]`).should('have.value', 'certainly symmetry whether');

      cy.get(`[data-cy="externalAgentDailyHoursAvg"]`).type('16121.57');
      cy.get(`[data-cy="externalAgentDailyHoursAvg"]`).should('have.value', '16121.57');

      cy.get(`[data-cy="dailyHourAvg"]`).type('15697.96');
      cy.get(`[data-cy="dailyHourAvg"]`).should('have.value', '15697.96');

      cy.get(`[data-cy="avgHoursToAnswerCalls"]`).type('24763');
      cy.get(`[data-cy="avgHoursToAnswerCalls"]`).should('have.value', '24763');

      cy.get(`[data-cy="totalHoursToAnswerCalls"]`).type('2537.85');
      cy.get(`[data-cy="totalHoursToAnswerCalls"]`).should('have.value', '2537.85');

      cy.get(`[data-cy="totalReceivedCalls"]`).type('2895');
      cy.get(`[data-cy="totalReceivedCalls"]`).should('have.value', '2895');

      cy.get(`[data-cy="totalAttendedCalls"]`).type('9869');
      cy.get(`[data-cy="totalAttendedCalls"]`).should('have.value', '9869');

      cy.get(`[data-cy="attendedCallsPercentage"]`).type('12314.56');
      cy.get(`[data-cy="attendedCallsPercentage"]`).should('have.value', '12314.56');

      cy.get(`[data-cy="avgDailyAttendedCalls"]`).type('14944.03');
      cy.get(`[data-cy="avgDailyAttendedCalls"]`).should('have.value', '14944.03');

      cy.get(`[data-cy="avgDailyAttendedCallsByExternal"]`).type('8419.79');
      cy.get(`[data-cy="avgDailyAttendedCallsByExternal"]`).should('have.value', '8419.79');

      cy.get(`[data-cy="avgDailyAttendedCallsByExternalByDay"]`).type('15811.54');
      cy.get(`[data-cy="avgDailyAttendedCallsByExternalByDay"]`).should('have.value', '15811.54');

      cy.get(`[data-cy="avgDailyAttendedCallsByInternal"]`).type('26574.18');
      cy.get(`[data-cy="avgDailyAttendedCallsByInternal"]`).should('have.value', '26574.18');

      cy.get(`[data-cy="totalReceivedChats"]`).type('28533');
      cy.get(`[data-cy="totalReceivedChats"]`).should('have.value', '28533');

      cy.get(`[data-cy="totalAttendedChats"]`).type('19077');
      cy.get(`[data-cy="totalAttendedChats"]`).should('have.value', '19077');

      cy.get(`[data-cy="totalReceivedMails"]`).type('7265');
      cy.get(`[data-cy="totalReceivedMails"]`).should('have.value', '7265');

      cy.get(`[data-cy="totalAttendedMails"]`).type('12079');
      cy.get(`[data-cy="totalAttendedMails"]`).should('have.value', '12079');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        numberOfHours = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', numberOfHoursPageUrlPattern);
    });
  });
});
