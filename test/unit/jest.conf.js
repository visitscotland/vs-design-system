const path = require("path")

module.exports = {
  rootDir: path.resolve(__dirname, "../../"),
  roots: ["<rootDir>/src"],
  testMatch: ["**/?(*.)jest.spec.js?(x)"],
  modulePaths: ["<rootDir>"],
  moduleFileExtensions: ["js", "json", "vue"],
  moduleNameMapper: {
    "^@/(.*)$": "<rootDir>/src/$1",
  },
  transform: {
    "^.+\\.js$": "<rootDir>/node_modules/babel-jest",
    ".*\\.(vue)$": "<rootDir>/node_modules/vue-jest",
  },
  transformIgnorePatterns: ["<rootDir>/node_modules/(?!(bootstrap-vue)/)"],
  snapshotSerializers: ["<rootDir>/node_modules/jest-serializer-vue"],
  setupFiles: ["<rootDir>/test/unit/setup"],
  coverageDirectory: "<rootDir>/test/unit/coverage",
  collectCoverageFrom: [
    "<rootDir>/src/**/*.{js,vue}",
    "!<rootDir>/cypress/**/*.{js,vue}",
    "!<rootDir>/**/*.spec.js",
    "!<rootDir>/src/main.js",
    "!<rootDir>/src/router/index.js",
    "!<rootDir>/node_modules/**",
    "!<rootDir>/src/system.js",
    "!<rootDir>/src/system-components.js",
    "!<rootDir>/docs/**/*",
  ],
}
