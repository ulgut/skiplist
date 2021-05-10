# Project Structure
### src/visuals/tsx
Main logic for visuals
### src/visuals/styles
Styling for animations
### src/visuals/skiplist
Holds TS implementations, which are direct compilations from our main Java implementations with a few exceptions:
1. Simplified for animations
2. Contain extra animation logic

# Local Deployment
1. Make sure you have a suitable version of typescript installed.
2. Install the project by moving into the vis subfolder ```cd vis``` and using ```npm install``` or ```yarn install ```.
3. Run by ```npm start``` and navigate to localhost:3000 in your browser.


# Animations Currently:
1. Insertion.
1. Get Path Tracing.

### Version History
v1.0 --- Add basic insertion grid<br/>
v1.1 --- Add functioning get method
