import React, { useState, useEffect } from 'react';
import { ComposedChart, Line, Bar, XAxis, YAxis, CartesianGrid, Tooltip, Legend } from 'recharts';
import useAuth from '../../hooks/useAuth';

const CombinedColumnLineChart = () => {
  const auth = useAuth();

  const [data, setData] = useState(null);

  async function getData(year, month) {
    try {
      // Địa chỉ API và tham số
      const apiUrl = `http://localhost:8080/transactions/monthly-revenue?year=${year}&month=${month}`;

      // Gọi API bằng phương thức GET
      const response = await fetch(apiUrl, {
        method: 'GET',
        headers: {
          'Authorization': `Bearer ${auth.accessToken}`
        }
      });
      // Kiểm tra trạng thái của response
      if (!response.ok) {
        throw new Error('Network response was not ok');
      }
      else {
        // Chuyển đổi response thành JSON
        const result = await response.json();
        // Cập nhật state với dữ liệu từ API
        console.log(result);
      }
    } catch (error) {
      console.error('Error fetching data:', error);
    }
  }

  useEffect(() => {
    getData(2023, 12);
  }, []);

  return (
    <>
      <div className="chart-container">
        <div className="chart-title">Biểu đồ cột kết hợp với đường</div>
        <ComposedChart width={600} height={300} data={data} margin={{ top: 5, right: 30, left: 20, bottom: 5 }}>
          <XAxis dataKey="name" />
          <YAxis />
          <CartesianGrid strokeDasharray="3 3" />
          <Tooltip />
          <Legend />
          <Bar dataKey="pv" barSize={20} fill="#8884d8" />
          <Line type="monotone" dataKey="uv" stroke="#82ca9d" />
        </ComposedChart>
      </div>

    </>
  );
};

export default CombinedColumnLineChart;
