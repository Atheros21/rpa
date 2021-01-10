using System.Collections;
using System.Collections.Generic;
using TMPro;
using UnityEngine;
using UnityEngine.UI;

public class UiManager : MonoBehaviour
{
    [SerializeField] private Button quitApp;
    [SerializeField] private TextMeshProUGUI country;
    [SerializeField] private TextMeshProUGUI population;
    [SerializeField] private TextMeshProUGUI increaseProcentage;
    [SerializeField] private TextMeshProUGUI increaseNumber;
    [SerializeField] private TextMeshProUGUI density;
    [SerializeField] private TextMeshProUGUI surface;
    [SerializeField] private TextMeshProUGUI emigrants;
    [SerializeField] private TextMeshProUGUI fertility;
    [SerializeField] private TextMeshProUGUI age;
    [SerializeField] private TextMeshProUGUI urban;
    [SerializeField] private TextMeshProUGUI world;
    [SerializeField] private string path;
    private string fileData;
    private string[] lines;

    private void Awake()
    {
        fileData = System.IO.File.ReadAllText(path);
        lines = fileData.Split("\n"[0]);
    }

    private void Start()
    {
        quitApp.onClick.AddListener(delegate { Application.Quit(); });
    }


    public void LoadInfo(string targetCountry)
    {
        if (lines == null)
        {
            fileData = System.IO.File.ReadAllText(path);
            lines = fileData.Split("\n"[0]);
        }

        foreach (var item in lines)
        {
            string[] lineData = (item.Trim()).Split("^"[0]);
            if(lineData.Length<10)
            {
                continue;
            }
            if (lineData[1] == targetCountry)
            {
                country.text = lineData[1];
                population.text = lineData[2];
                increaseProcentage.text = lineData[3];
                increaseNumber.text = lineData[4];
                density.text = lineData[5];
                surface.text = lineData[6];
                emigrants.text = lineData[7];
                fertility.text = lineData[8];
                age.text = lineData[9];
                urban.text = lineData[10];
                world.text = lineData[11];
                return;
            }
        }

        population.text = "";
        increaseProcentage.text = "";
        increaseNumber.text = "";
        density.text = "";
        surface.text = "";
        emigrants.text = "";
        fertility.text = "";
        age.text = "";
        urban.text = "";
        world.text = "";
    }
}
