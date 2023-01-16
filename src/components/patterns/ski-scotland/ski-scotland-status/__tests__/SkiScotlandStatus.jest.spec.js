import { mount } from '@vue/test-utils';
import moxios from 'moxios';
import VsSkiScotlandStatus from '../SkiScotlandStatus';

const sampleSkiData = require('./assets/sample-ski-data.json');

const centreInformationSlot = 'Centre Information Placeholder';
const skiStatusUrl = 'https://testurl';

const closedLabel = 'fermé';
const currentWeatherLabel = 'météo actuelle';
const difficultLabel = 'difficile';
const easyLabel = 'facile';
const intermediateLabel = 'intermédiaire';
const itinerariesLabel = 'itinéraires';
const lastUpdatedLabel = 'dernière mise à jour';
const liftsLabel = 'ascenseurs';
const liftStatusLabel = 'état des ascenseurs';
const newsLabel = 'l\'actualités';
const openLabel = 'ouvrir';
const openingLabel = 'ouverture';
const otherLabel = 'autre';
const roadsLabel = 'routes';
const runsLabel = 'court';
const runsLiftsStatusLabel = 'statut de course/levage';
const runStatusLabel = 'statut d\'exécution';
const snowConditionsLabel = 'conditions d\'enneigement';
const statusLabel = 'statut';
const veryDifficultLabel = 'très difficile';
const weatherForecastLabel = 'prévisions météorologiques';

const factoryMount = (propsData) => mount(VsSkiScotlandStatus, {
    propsData: {
        skiStatusUrl,
        closedLabel,
        currentWeatherLabel,
        difficultLabel,
        easyLabel,
        intermediateLabel,
        itinerariesLabel,
        lastUpdatedLabel,
        liftsLabel,
        liftStatusLabel,
        newsLabel,
        openLabel,
        openingLabel,
        otherLabel,
        roadsLabel,
        runsLabel,
        runsLiftsStatusLabel,
        runStatusLabel,
        snowConditionsLabel,
        statusLabel,
        veryDifficultLabel,
        weatherForecastLabel,
        ...propsData,
    },
});

let wrapper;
beforeEach(() => {
    moxios.install();

    moxios.stubRequest(skiStatusUrl, {
        status: 200,
        response: sampleSkiData,
    });

    wrapper = factoryMount();
});

afterEach(() => {
    moxios.uninstall();
});

describe('VsSkiScotlandStatus', () => {
    describe(':props', () => {
        it('should render a component with the data-test attribute `vs-ski-scotland-status`', () => {
            expect(wrapper.find('[data-test="vs-ski-scotland-status"]').exists()).toBe(true);
        });

        it('should not render the js-disabled block when js is present', () => {
            expect(wrapper.find('[data-test="vs-ski__js-disabled"]').exists()).toBe(false);
        });

        it(':closedLabel - should render the label in the appropriate location', () => {
            const container = wrapper.find('[data-test="vs-ski__closed-label"]');
            expect(container.html()).toContain(closedLabel);
        });

        it(':openingLabel - should render the label in the appropriate location', () => {
            const container = wrapper.find('[data-test="vs-ski__opening-label"]');
            expect(container.html()).toContain(openingLabel);
        });

        it(':openLabel - should render the label in the appropriate location', () => {
            const container = wrapper.find('[data-test="vs-ski__open-label"]');
            expect(container.html()).toContain(openLabel);
        });

        it(':easyLabel - should render the label in the appropriate location', () => {
            const container = wrapper.find(`[data-test="vs-ski__${easyLabel}-label"]`);
            expect(container.html()).toContain(easyLabel);
        });

        it(':intermediateLabel - should render the label in the appropriate location', () => {
            const container = wrapper.find(`[data-test="vs-ski__${intermediateLabel}-label"]`);
            expect(container.html()).toContain(intermediateLabel);
        });

        it(':difficultLabel - should render the label in the appropriate location', () => {
            const container = wrapper.find(`[data-test="vs-ski__${difficultLabel}-label"]`);
            expect(container.html()).toContain(difficultLabel);
        });

        it(':veryDifficultLabel - should render the label in the appropriate location', () => {
            const container = wrapper.find(`[data-test="vs-ski__${veryDifficultLabel}-label"]`);
            expect(container.html()).toContain(veryDifficultLabel);
        });

        it(':otherLabel - should render the label in the appropriate location', () => {
            const container = wrapper.find(`[data-test="vs-ski__${otherLabel}-label"]`);
            expect(container.html()).toContain(otherLabel);
        });

        it(':itinerariesLabel - should render the label in the appropriate location', () => {
            const container = wrapper.find(`[data-test="vs-ski__${itinerariesLabel}-label"]`);
            expect(container.html()).toContain(itinerariesLabel);
        });

        it(':currentWeatherLabel - should render the label in the appropriate location', () => {
            const container = wrapper.find('[data-test="vs-ski__current-weather-label"]');
            expect(container.html()).toContain(currentWeatherLabel);
        });

        it(':weatherForecastLabel - should render the label in the appropriate location', () => {
            const container = wrapper.find('[data-test="vs-ski__weather-forecast-label"]');
            expect(container.html()).toContain(weatherForecastLabel);
        });

        it(':roadsLabel - should render the label in the appropriate location', () => {
            const container = wrapper.find('[data-test="vs-ski__roads-label"]');
            expect(container.html()).toContain(roadsLabel);
        });

        it(':newsLabel - should render the label in the appropriate location', () => {
            const container = wrapper.find('[data-test="vs-ski__news-label"]');
            expect(container.html()).toContain(newsLabel);
        });

        it(':snowConditionsLabel - should render the label in the appropriate location', () => {
            const container = wrapper.find('[data-test="vs-ski__snow-conditions-label"]');
            expect(container.html()).toContain(snowConditionsLabel);
        });

        it(':lastUpdatedLabel - should render the label in the appropriate location', () => {
            const container = wrapper.find('[data-test="vs-ski__last-updated-label"]');
            expect(container.html()).toContain(lastUpdatedLabel);
        });

        it(':liftsLabel - should render the label in the appropriate location', () => {
            const container = wrapper.find('[data-test="vs-ski__lifts-label"]');
            expect(container.html()).toContain(liftsLabel);
        });

        it(':liftStatusLabel - should render the label in the appropriate location', () => {
            const container = wrapper.find('[data-test="vs-ski__lift-status-label"]');
            expect(container.html()).toContain(liftStatusLabel);
        });

        it(':runsLabel - should render the label in the appropriate location', () => {
            const container = wrapper.find('[data-test="vs-ski__runs-label"]');
            expect(container.html()).toContain(runsLabel);
        });

        it(':runsLiftsStatusLabel - should render the label in the appropriate location', () => {
            const container = wrapper.find('[data-test="vs-ski__runs-lifts-status-label"]');
            expect(container.html()).toContain(runsLiftsStatusLabel);
        });

        it(':runStatusLabel - should render the label in the appropriate location', () => {
            const container = wrapper.find('[data-test="vs-ski__run-status-label"]');
            expect(container.html()).toContain(runStatusLabel);
        });

        it(':statusLabel - should render the label in the appropriate location', () => {
            const container = wrapper.find('[data-test="vs-ski__status-label"]');
            expect(container.html()).toContain(statusLabel);
        });
    });

    describe(':slots', () => {
        it('should render content inserted into `centre-information` slot', () => {
            const modifiedWrapper = mount(VsSkiScotlandStatus, {
                slots: {
                    'centre-information': centreInformationSlot,
                },
            });

            expect(modifiedWrapper.text()).toContain(centreInformationSlot);
        });
    });
});
