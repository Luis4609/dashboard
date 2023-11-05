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

describe('DailyCalls e2e test', () => {
  const dailyCallsPageUrl = '/daily-calls';
  const dailyCallsPageUrlPattern = new RegExp('/daily-calls(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const dailyCallsSample = {
    totalDailyAttendedCalls: 17572,
    totalDailyMissedCalls: 24254,
    totalDailyReceivedCallsExternalAgent: 19944,
    totalDailyAttendedCallsExternalAgent: 32409,
    totalDailyAttendedCallsInternalAgent: 9731,
    totalDailyCallsTimeInMin: 22170,
    totalDailyCallsTimeExternalAgentInMin: 19576,
    totalDailyCallsTimeInternalAgentInMin: 25357,
    avgDailyOperationTimeExternalAgentInMin: 26770.87,
  };

  let dailyCalls;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/daily-calls+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/daily-calls').as('postEntityRequest');
    cy.intercept('DELETE', '/api/daily-calls/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (dailyCalls) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/daily-calls/${dailyCalls.id}`,
      }).then(() => {
        dailyCalls = undefined;
      });
    }
  });

  it('DailyCalls menu should load DailyCalls page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('daily-calls');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('DailyCalls').should('exist');
    cy.url().should('match', dailyCallsPageUrlPattern);
  });

  describe('DailyCalls page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(dailyCallsPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create DailyCalls page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/daily-calls/new$'));
        cy.getEntityCreateUpdateHeading('DailyCalls');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', dailyCallsPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/daily-calls',
          body: dailyCallsSample,
        }).then(({ body }) => {
          dailyCalls = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/daily-calls+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/daily-calls?page=0&size=20>; rel="last",<http://localhost/api/daily-calls?page=0&size=20>; rel="first"',
              },
              body: [dailyCalls],
            },
          ).as('entitiesRequestInternal');
        });

        cy.visit(dailyCallsPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details DailyCalls page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('dailyCalls');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', dailyCallsPageUrlPattern);
      });

      it('edit button click should load edit DailyCalls page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('DailyCalls');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', dailyCallsPageUrlPattern);
      });

      it('edit button click should load edit DailyCalls page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('DailyCalls');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', dailyCallsPageUrlPattern);
      });

      it('last delete button click should delete instance of DailyCalls', () => {
        cy.intercept('GET', '/api/daily-calls/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('dailyCalls').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', dailyCallsPageUrlPattern);

        dailyCalls = undefined;
      });
    });
  });

  describe('new DailyCalls page', () => {
    beforeEach(() => {
      cy.visit(`${dailyCallsPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('DailyCalls');
    });

    it('should create an instance of DailyCalls', () => {
      cy.get(`[data-cy="totalDailyReceivedCalls"]`).type('28509');
      cy.get(`[data-cy="totalDailyReceivedCalls"]`).should('have.value', '28509');

      cy.get(`[data-cy="totalDailyAttendedCalls"]`).type('5103');
      cy.get(`[data-cy="totalDailyAttendedCalls"]`).should('have.value', '5103');

      cy.get(`[data-cy="totalDailyMissedCalls"]`).type('6793');
      cy.get(`[data-cy="totalDailyMissedCalls"]`).should('have.value', '6793');

      cy.get(`[data-cy="totalDailyReceivedCallsExternalAgent"]`).type('12385');
      cy.get(`[data-cy="totalDailyReceivedCallsExternalAgent"]`).should('have.value', '12385');

      cy.get(`[data-cy="totalDailyAttendedCallsExternalAgent"]`).type('16763');
      cy.get(`[data-cy="totalDailyAttendedCallsExternalAgent"]`).should('have.value', '16763');

      cy.get(`[data-cy="totalDailyReceivedCallsInternalAgent"]`).type('6664');
      cy.get(`[data-cy="totalDailyReceivedCallsInternalAgent"]`).should('have.value', '6664');

      cy.get(`[data-cy="totalDailyAttendedCallsInternalAgent"]`).type('30668');
      cy.get(`[data-cy="totalDailyAttendedCallsInternalAgent"]`).should('have.value', '30668');

      cy.get(`[data-cy="totalDailyCallsTimeInMin"]`).type('16419');
      cy.get(`[data-cy="totalDailyCallsTimeInMin"]`).should('have.value', '16419');

      cy.get(`[data-cy="totalDailyCallsTimeExternalAgentInMin"]`).type('24703');
      cy.get(`[data-cy="totalDailyCallsTimeExternalAgentInMin"]`).should('have.value', '24703');

      cy.get(`[data-cy="totalDailyCallsTimeInternalAgentInMin"]`).type('17013');
      cy.get(`[data-cy="totalDailyCallsTimeInternalAgentInMin"]`).should('have.value', '17013');

      cy.get(`[data-cy="avgDailyOperationTimeExternalAgentInMin"]`).type('19419.87');
      cy.get(`[data-cy="avgDailyOperationTimeExternalAgentInMin"]`).should('have.value', '19419.87');

      cy.get(`[data-cy="avgDailyOperationTimeInternalAgentInMin"]`).type('3876.13');
      cy.get(`[data-cy="avgDailyOperationTimeInternalAgentInMin"]`).should('have.value', '3876.13');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        dailyCalls = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', dailyCallsPageUrlPattern);
    });
  });
});
