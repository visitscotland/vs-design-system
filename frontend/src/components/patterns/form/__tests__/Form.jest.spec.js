import { shallowMount } from '@vue/test-utils';
import VsForm from '../Form';

jest.mock('axios', () => ({
    get: () => Promise.resolve(
        {
            data: {
                fields: [
                    {
                        name: 'firstName',
                        element: 'input',
                        type: 'text',
                        label: 'First name',
                    },
                    {
                        name: 'lastName',
                        element: 'input',
                        type: 'text',
                        label: 'Last name',
                    },
                    {
                        name: 'email',
                        element: 'input',
                        type: 'email',
                        label: 'Email address',
                    },
                ],
            },
        },
    ),
}));

const factoryShallowMount = () => shallowMount(VsForm, {
    propsData: {
    },
});

beforeEach(() => {
    window.MktoForms2 = {
        loadForm: jest.fn(),
    };
});

describe('VsForm', () => {
    it('should render a component with the data-test attribute `vs-form`', () => {
        const wrapper = factoryShallowMount();
        expect(wrapper.find('[data-test="vs-form"]').exists()).toBe(true);
    });

    it('should render three input fields', async() => {
        const wrapper = factoryShallowMount();
        await wrapper.vm.$nextTick();
        const allInputs = wrapper.findAll('vsforminput-stub');

        expect(allInputs.length).toBe(3);
    });
});
