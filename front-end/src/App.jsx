import { useState } from 'react'
import './App.css'
import 'bootstrap/dist/css/bootstrap.css'
import HomepageHeader from './homepage/header'
import Register from './component/Register'
import Content from './homepage/Content'

function App() {

  return (
    <>
      <HomepageHeader />
      <Content />
    </>
  )
}

export default App
