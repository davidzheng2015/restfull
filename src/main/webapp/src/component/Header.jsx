import React from 'react';
import {
	Row, Col, Icon, Badge
}
from 'antd';
import UserPanel from './UserPanel';
const Header = React.createClass({
	getInitialState() {
			return {
				show: true
			};
		},
		handleClick() {
			this.setState({
				show: !this.state.show
			});
		},
		render() {
			return (
				<Row type = "flex" align ="bottom">
			<Col span="2"></Col>
			<Col span="4">logo</Col>
			<Col span = "14"></Col>
			<Col span="1"><Badge dot={this.state.show}><a href="#" onClick={this.handleClick}><Icon type="notification" style={{fontSize:'18px'}}/></a></Badge></Col>
			<Col span = "3"><UserPanel /></Col>
			</Row>
			);
		}
});

export default Header;