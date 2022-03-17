import requireContext from 'require-context.macro';

export default requireContext('../assets/svg?optimise', true, /^\.\/.*\.svg$/);
